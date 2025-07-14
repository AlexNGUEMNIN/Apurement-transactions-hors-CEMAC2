package org.example.gestiondepassementplafond.services.documents;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.poi.ss.usermodel.*;
import org.example.gestiondepassementplafond.modeles.ExcelData;
import org.example.gestiondepassementplafond.services.client.ClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

@Service("document1")
@Slf4j
public class DocumentServiceImpl implements DocumentService {

    private final WebClient webClient;

    @Value("${server1.ip}")
    String ipServer;

    @Value("${server1.path}")
    String path;

    @Value("${server1.port}")
    String port;

    @Value("${server1.saveData2}")
    String savePath;

    @Value("${server1.user}")
    String user;

    @Value("${server1.password}")
    String password;

    @Value("${server1.nomFichier}")
    String nomFichier;


    ClientService clientService;

    private static final Logger logger = LoggerFactory.getLogger(DocumentServiceImpl.class);

    @Autowired
    public DocumentServiceImpl(@Qualifier("clientImpl1") ClientService clientService) {
        this.clientService = clientService;
        this.webClient = WebClient
                .builder()
                .baseUrl(ipServer)
                .defaultHeader(HttpHeaders.USER_AGENT, "Mozilla/5.0")
                .build();
    }

    @Override
    public Mono<Boolean> importDocument() {
        return Mono.fromCallable(() -> {
            FTPClient ftpClient = new FTPClient();
            try {
                System.out.println("ip server = "+ipServer);
                System.out.println("Port = "+port);

                ftpClient.connect(ipServer, Integer.parseInt(port));
                boolean login = ftpClient.login(user,  password);
                if (!login) throw new RuntimeException("Login failed");

                ftpClient.enterLocalPassiveMode();
                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

                LocalDateTime maintenant = LocalDateTime.now();

                // 🌍 Appliquer le fuseau horaire
                ZonedDateTime zonedDateTime = maintenant.atZone(ZoneId.systemDefault());

                // ⏱️ Convertir en millis depuis 01/01/1970 UTC
                long millis = zonedDateTime.toInstant().toEpochMilli();

                StringBuilder lienFichier = new StringBuilder(path+nomFichier);
                StringBuilder savepath2 = new StringBuilder(savePath).append("fichier").append(millis).append(".xlsx");

                System.out.println("Chemin vers le fichier");
                System.out.println(lienFichier);

                String remotePath = lienFichier.toString();

                try (OutputStream outputStream = new FileOutputStream(savepath2.toString())) {
                    return ftpClient.retrieveFile(remotePath, outputStream);
                }
            } finally {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            }
        }).subscribeOn(Schedulers.boundedElastic()); // run in separate thread
    }

    @Override
    public Mono<Void> saveDocument() {
        return null;
    }

   // savePath+nomFichier

    @Override
    public Flux<Map<String, Object>> readExcel() {



        return Mono.fromCallable(() -> {
                try (FileInputStream file = new FileInputStream(savePath+nomFichier);
                     Workbook workbook = WorkbookFactory.create(file)) {

                    Sheet sheet = workbook.getSheetAt(0);
                    List<Map<String, Object>> rows = new ArrayList<>();

                    // Lire l'en-tête pour obtenir les noms des colonnes
                    Row headerRow = sheet.getRow(0);
                    List<String> headers = new ArrayList<>();

                    if (headerRow != null) {
                        for (Cell cell : headerRow) {
                            headers.add(getCellValue(cell).toString());
                        }
                    }

                    // Lire les données
                    for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                        Row row = sheet.getRow(i);
                        if (row != null) {
                            Map<String, Object> rowData = new HashMap<>();

                            for (int j = 0; j < headers.size() && j < row.getLastCellNum(); j++) {
                                Cell cell = row.getCell(j);
                                Object value = cell != null ? getCellValue(cell) : "";
                                rowData.put(headers.get(j), value);

                            }

                            rows.add(rowData);
                        }
                    }

                    return rows;
                }
            })
            .subscribeOn(Schedulers.boundedElastic()) // Traitement asynchrone
            .flatMapIterable(rows -> rows) // Convertir la liste en flux
            .doOnNext(row -> logger.info("Traitement de la ligne: {}", row))
            .onErrorResume(throwable -> {
                logger.error("Erreur lors de la lecture du fichier Excel", throwable);
                return Flux.error(new RuntimeException("Erreur de lecture Excel: " + throwable.getMessage()));
            });

    }

    private Object getCellValue(Cell cell) {
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue();
                } else {
                    return cell.getNumericCellValue();
                }
            case BOOLEAN:
                return cell.getBooleanCellValue();
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }


    private Flux<Map<String, Object>> processChunk(Sheet sheet, List<String> headers, List<Integer> rowIndexes) {
        return Flux.fromIterable(rowIndexes)
                .map(rowIndex -> {
                    Row row = sheet.getRow(rowIndex);
                    Map<String, Object> rowData = new HashMap<>();

                    if (row != null) {
                        for (int j = 0; j < headers.size() && j < row.getLastCellNum(); j++) {
                            Cell cell = row.getCell(j);
                            Object value = cell != null ? getCellValue(cell) : "";
                            rowData.put(headers.get(j), value);
                        }
                        rowData.put("_rowIndex", rowIndex);
                    }

                    return rowData;
                })
                .doOnNext(row -> logger.info("Chunk traité - Ligne: {}", row.get("_rowIndex")));
    }

    @Override
    public Flux<Map<String, Object>> readExcelFileParallel(int chunkSize) {
        return Mono.fromCallable(() -> {
                try (FileInputStream file = new FileInputStream(savePath+nomFichier);

                     Workbook workbook = WorkbookFactory.create(file)) {

                    System.out.println("row");
                    Sheet sheet = workbook.getSheetAt(0);

                    // Lire l'en-tête
                    Row headerRow = sheet.getRow(0);
                    List<String> headers = new ArrayList<>();

                    if (headerRow != null) {
                        for (Cell cell : headerRow) {
                            headers.add(getCellValue(cell).toString());
                        }
                    }

                    // Créer des chunks de lignes pour traitement parallèle
                    List<List<Integer>> chunks = new ArrayList<>();
                    List<Integer> currentChunk = new ArrayList<>();

                    DataFormatter dataFormatter = new DataFormatter();

                    List<Map<String, String>> rows = new ArrayList<>();

                    for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                        Row row = sheet.getRow(i);
                        if (row == null) continue;

                        Map<String, String> rowData = new HashMap<>();
                        for (int j = 0; j < headers.size(); j++) {
                            Cell cell = row.getCell(j);
                            String value = (cell != null) ? dataFormatter.formatCellValue(cell) : "";
                            rowData.put(headers.get(j), value);
                        }

                        System.out.println("Row " + i + ": " + rowData); // ou logger.info(...)
                        rows.add(rowData);
                    }

                    if (!currentChunk.isEmpty()) {
                        chunks.add(currentChunk);
                    }

                    return new ExcelData(sheet, headers, chunks);
                }
            })
            .subscribeOn(Schedulers.boundedElastic())
            .flatMapMany(excelData ->
                    Flux.fromIterable(excelData.chunks)
                            .parallel(Runtime.getRuntime().availableProcessors()) // Traitement parallèle
                            .runOn(Schedulers.boundedElastic())
                            .flatMap(chunk -> processChunk(excelData.sheet, excelData.headers, chunk))
                            .sequential()
            )
            .onErrorResume(throwable -> {
                logger.error("Erreur lors du traitement parallèle", throwable);
                return Flux.error(new RuntimeException("Erreur de traitement parallèle: " + throwable.getMessage()));
            });
    }
}
