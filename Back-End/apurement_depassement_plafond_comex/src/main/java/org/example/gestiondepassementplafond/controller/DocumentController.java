paage org.example.gestiondepassementplafond.controller;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.gestiondepassementplafond.modeles.Client;
import org.example.gestiondepassementplafond.services.client.ClientService;
import org.example.gestiondepassementplafond.services.documents.DocumentService;
import org.example.gestiondepassementplafond.services.listeoperation.ListeOperationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("apure_comex")
public class DocumentController {
    private static final Logger log = LoggerFactory.getLogger(DocumentController.class);

    //private static final Logger logger = (Logger) LoggerFactory.getLogger(DocumentController.class);

    @Value("${server1.nomFichier}")
    String nomFichier;

    @Value("${server1.dossierFichier}")
    String dossierPrinc;

    @Value("${server1.output}")
    String output;

    @Qualifier("document1")
    private final DocumentService documentService;

    @Qualifier("listoperation1")
    private final ListeOperationService listeOperationService;

    @Qualifier("clientImpl1")
    private final ClientService clientService;

    public DocumentController(@Qualifier("document1") DocumentService documentService, ListeOperationService listeOperationService, ClientService clientService) {
        this.documentService = documentService;
        this.listeOperationService = listeOperationService;
        this.clientService = clientService;
    }

    @GetMapping("/telecharger")
    public Mono<String> getDocument(){
        return documentService.importDocument().map(success -> success ? "Download Success" : "Download Failed");
    }

    @GetMapping(value = "/readFileParallele", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<Map<String, Object>> readFileParallele(@RequestParam(defaultValue = "10") int chunkSize){

        return documentService.readExcelFileParallel(chunkSize)
                .delayElements(Duration.ofMillis(50))
                .doOnError(error -> System.err.println("Erreur dans readExcelParallel: " + error.getMessage()));
    }

    @GetMapping(value = "/readFile", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<Map<String, Object>> readFile(@RequestParam(defaultValue = "10") int chunkSize){

        return documentService.readExcel()
                .delayElements(Duration.ofMillis(100)) // Délai pour simulation du streaming
                .doOnError(error -> System.err.println("Erreur dans readExcel: " + error.getMessage()));

    }

    //db/files/fichier_monetique.xlsx

    @GetMapping(value = "/readFile2", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Map<String, String>> streamExcelData() {
        try {
            ClassPathResource resource = new ClassPathResource(dossierPrinc + nomFichier);
            InputStream inputStream = resource.getInputStream();
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);

            // Lecture de la première ligne (entêtes)
            Row headerRow = sheet.getRow(0);
            List<String> headers = new ArrayList<>();
            headerRow.forEach(cell -> headers.add(cell.getStringCellValue()));

            // Délégation au service pour récupérer l’opération id=1
            return listeOperationService.getOperationById(1L)
                    .flatMapMany(operations -> {
                        // Ici, on est sûr d’avoir l’objet `operations`
                        System.out.println("Operation récupérée : " + operations);

                        // On transforme chaque ligne du sheet en un Map<String, String>
                        // en chiffrant aussi l’appel à saveDepassementClient pour chaque client
                        return Flux.range(1, sheet.getLastRowNum())
                                .map(sheet::getRow)                  // Récupère la ligne Excel
                                .filter(Objects::nonNull)            // Ignore les lignes vides
                                .flatMap(row -> {

                                    System.out.println("************************************");
                                    System.out.println("************************************");
                                    System.out.println("************************************");
                                    System.out.println(row);
                                    System.out.println(row.getCell(7).getStringCellValue());
                                    System.out.println("------------------------------------");
                                    System.out.println("------------------------------------");
                                    System.out.println("------------------------------------");

                                    // Construction de l’objet Client basé sur la ligne Excel
                                    Client client = new Client(
                                            null,
                                            row.getCell(10).getStringCellValue(),
                                            row.getCell(8).getStringCellValue(),
                                            row.getCell(7).getStringCellValue().substring(0, 7),
                                            "Reseau " + row.getRowNum(),
                                            row.getCell(0).getStringCellValue(),
                                            "Agence " + 1,
                                            row.getCell(8).getStringCellValue(),
                                            "677274218",
                                            "njikidenis@gmail.com",
                                            152400000.0,
                                            02,
                                            "2024",
                                            30,
                                            LocalDate.now(),
                                            row.getCell(0).getStringCellValue()
                                    );

                                    // Construire la map de données à renvoyer
                                    Map<String, String> rowData = new HashMap<>();
                                    for (int j = 0; j < headers.size(); j++) {
                                        Cell cell = row.getCell(j);
                                        String value = (cell != null) ? new DataFormatter().formatCellValue(cell) : "";
                                        rowData.put(headers.get(j), value);
                                    }

                                    // Sauvegarde réactive du client, puis on renvoie la map
                                    return clientService.saveDepassementClient(client, operations)
                                            .thenReturn(rowData);
                                })
                                .doFinally(signalType -> {
                                    // On ferme le workbook et l’inputStream à la fin du flux
                                    try {
                                        workbook.close();
                                        inputStream.close();
                                    } catch (IOException ignored) { }
                                });
                    })
                    // Si l’opération n’existe pas (Mono vide), on renvoie un flux vide
                    .switchIfEmpty(Flux.empty());

        } catch (IOException e) {
            // En cas d’erreur d’ouverture du fichier, on renvoie un Flux.error
            return Flux.error(e);
        }
    }
}
