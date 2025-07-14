package org.example.gestiondepassementplafond.services.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.example.gestiondepassementplafond.dto.ClientOperationDto;
import org.example.gestiondepassementplafond.dto.OperationDto;
import org.example.gestiondepassementplafond.modeles.*;
import org.example.gestiondepassementplafond.repositories.*;
import org.example.gestiondepassementplafond.services.EmailService;
import org.example.gestiondepassementplafond.services.clientoperations.ClientOperationService;
import org.example.gestiondepassementplafond.services.plafond2.PlafondNonApureService;
import org.example.gestiondepassementplafond.services.taberreur.TabErreurService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringWebFluxTemplateEngine;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service("clientImpl1")
public class ClientServiceImpl implements ClientService {

    private static final Logger log = LoggerFactory.getLogger(ClientServiceImpl.class);
    private final RepartitionEnvoiRepository repartitionEnvoiRepository;
    private final CanalOperationRepository canalOperationRepository;
    private final CanalRepository canalRepository;
    private final CanalMessageEnvoiRepository canalMessageEnvoiRepository;
    private final VariableRepartitionRepository variableRepartitionRepository;
    private final EmailService emailService;
    private final TabErreurService tabErreurService;
    private final ClientOperationService clientOperationService;
    private final PlafondNonApureService plafondNonApureService;

    private final ClientRepository clientRepository;
    private final ClientOperationRepository clientOperationRepository;
    private final ListMessageRepository listMessageRepository;
    private final ListeOperationRepository listeOperationRepository;
    private final ListeParametreRepository listeParametreRepository;
    private OperationDto operationDto;

    @Value("${server1.nbreApurComexPlaf}")
    public int nbreJrApur;

    @Value("${server1.libCirculaire}")
    public String libCirculaire;

    @Value("${server1.dureeDelaiApresMiseendemeure}")
    public int dureeDelaiApresMiseendemeure;

    @Value("${server1.libDelaiApresMiseendemeure}")
    public String libDelaiApresMiseendemeure;

    @Value("${server1.lienWhatsapp}")
    public String lienWhatsapp;

    @Value("${server1.lienMail}")
    public String lienMail;



    private final SpringWebFluxTemplateEngine engine;

    public ClientServiceImpl(RepartitionEnvoiRepository repartitionEnvoiRepository, CanalOperationRepository canalOperationRepository, CanalRepository canalRepository, CanalMessageEnvoiRepository canalMessageEnvoiRepository, VariableRepartitionRepository variableRepartitionRepository, EmailService emailService, @Lazy @Qualifier("taberreur1") TabErreurService tabErreurService, ClientOperationService clientOperationService, @Qualifier("plafondnonapure1") PlafondNonApureService plafondNonApureService, ClientRepository clientRepository, ClientOperationRepository clientOperationRepository, ListMessageRepository listMessageRepository, ListeOperationRepository listeOperationRepository, ListeParametreRepository listeParametreRepository, SpringWebFluxTemplateEngine engine) {
        this.repartitionEnvoiRepository = repartitionEnvoiRepository;
        this.canalOperationRepository = canalOperationRepository;
        this.canalRepository = canalRepository;
        this.canalMessageEnvoiRepository = canalMessageEnvoiRepository;
        this.variableRepartitionRepository = variableRepartitionRepository;
        this.emailService = emailService;
        this.tabErreurService = tabErreurService;
        this.clientOperationService = clientOperationService;
        this.plafondNonApureService = plafondNonApureService;
        this.clientRepository = clientRepository;
        this.clientOperationRepository = clientOperationRepository;
        this.listMessageRepository = listMessageRepository;
        this.listeOperationRepository = listeOperationRepository;
        this.listeParametreRepository = listeParametreRepository;
        this.engine = engine;
    }

    @Override
    public Mono<Client> createClient(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public Flux<Client> getAllClient() {
        return clientRepository.findAll();
    }

    @Override
    public Mono<Boolean> deleteClient(Long clientId) {
        return clientRepository.deleteClientByIdClient(clientId);
    }

    @Override
    public Mono<Client> updateClient(Long clientId, Client newClient) {
        return clientRepository.findById(clientId)
                .doOnNext(clientFromDb -> {
                    clientFromDb.setNomClient(newClient.getNomClient());
                    clientFromDb.setPrenomClient(newClient.getPrenomClient());
                    clientFromDb.setAgence(newClient.getAgence());
                    clientFromDb.setReseau(newClient.getReseau());
                    clientFromDb.setNumCarte(newClient.getNumCarte());
                    clientFromDb.setAnneeVoyage(newClient.getAnneeVoyage());
                    clientFromDb.setCumulTrans(newClient.getCumulTrans());
                    clientFromDb.setNomPorteur(newClient.getNomPorteur());
                    clientFromDb.setEmailClt(newClient.getEmailClt());
                    clientFromDb.setNbreJrApur(newClient.getNbreJrApur());
                    clientFromDb.setMoisVoyage(newClient.getMoisVoyage());
                    clientFromDb.setNumTel(newClient.getNumTel());
                })
                .flatMap(clientRepository::save);
    }

    @Override
    public Mono<Client> getClientById(Long clientId) {
        return clientRepository.findById(clientId);
    }

    public Mono<String> renderHtml(String template, Map<String,Object> vars) {
        Context ctx = new Context(Locale.getDefault());
        vars.forEach(ctx::setVariable);
        return Mono.fromCallable(() -> engine.process(template, ctx));
    }

    /** Génère un PDF à partir du HTML */
    public Mono<byte[]> htmlToPdf(byte[] htmlBytes) {
        return Mono.fromCallable(() -> {
            try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
                PdfRendererBuilder builder = new PdfRendererBuilder();
                builder.withHtmlContent(new String(htmlBytes, StandardCharsets.UTF_8), null);
                builder.toStream(out);
                builder.run();
                return out.toByteArray();
            }
        });
    }

    public Mono<Path> renderAndSavePdf(String template, Map<String,Object> vars, Path targetFile) {
        return renderHtml(template, vars)
                .map(String::getBytes)
                .flatMap(this::htmlToPdf)
                .flatMap(bytes -> Mono.fromCallable(() -> {
                    Files.createDirectories(targetFile.getParent());
                    Files.write(targetFile, bytes);
                    return targetFile;
                }));
    }

    public Mono<ClientOperationDto> getClientOperationDto(OperationDto operationDto, Client client) {
        List<RepartitionEnvoi> repartitionEnvoi = operationDto.getRepartitionEnvoi();
        System.out.println("Repartition des envoirs === "+repartitionEnvoi.size());

        LocalDate dateFin = client.getDateSaisie().plusDays(operationDto.getNbreJour());

        // Configure ObjectMapper for LocalDate conversion
        ObjectMapper mapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        Map<String,Object> mappageDonnee = mapper.convertValue(client, Map.class);

        // Build and save ClientOperations
        ClientOperations toSave = new ClientOperations(
                null,
                client.getIdClient(),
                operationDto.getIdOperation(),
                false,
                client.getDateSaisie(),
                dateFin,
                repartitionEnvoi.size()
        );

        return clientOperationRepository.save(toSave)
                .flatMap(savedOp -> {
                    Long clientOpId = savedOp.getIdClientOperation();
                    LocalDate baseDate = client.getDateSaisie();

                    // 1) Flux to generate & save ListMessage PDFs
                    Flux<ListMessage> listMsgFlux = Flux.fromIterable(repartitionEnvoi)
                            .index()
                            .flatMap(tuple -> {

                                int index = Math.toIntExact(tuple.getT1());
                                RepartitionEnvoi rep = tuple.getT2();

                                int jours = rep.getRatio()
                                        .multiply(BigDecimal.valueOf(operationDto.getNbreJour()))
                                        .intValue();

                                String templateName = rep.getModelFichier();
                                // 2025-04-03 15:02:33
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                                DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                                LocalDate dateTrans = LocalDate.parse(client.getDateTrans(), formatter);

                                LocalDate dateApur = dateTrans.plusDays(nbreJrApur);

                                BigDecimal nbreJourEcoule = rep.getRatio().multiply(BigDecimal.valueOf(nbreJrApur));
                                //long nbreJourEcoule = ChronoUnit.DAYS.between(dateTrans, LocalDate.now());

                                LocalDate dateEnvoi = client.getDateSaisie().plusDays(nbreJourEcoule.intValue());

                                Map<String,Object> vars = Map.ofEntries(
                                        Map.entry("nomClient", client.getNomClient()),
                                        Map.entry("matriculeClient", client.getMatriculeClient()),
                                        Map.entry("dateSaisie", client.getDateSaisie().toString()),
                                        Map.entry("nbreJr", nbreJrApur),
                                        Map.entry("numCarte", client.getNumCarte()),
                                        Map.entry("nbreJrEcoule", nbreJourEcoule.intValue()),
                                        Map.entry("nbreJrRestant", nbreJrApur - nbreJourEcoule.intValue()),
                                        Map.entry("dateTransaction", client.getDateTrans()),
                                        Map.entry("numSerie", 0),
                                        Map.entry("dateMiseDem", dateApur.format(formatter2)),
                                        Map.entry("annee", String.valueOf(LocalDate.now().getYear())),
                                        Map.entry("libCirculaire", libCirculaire),
                                        Map.entry("dureeDelaiApresMiseendemeure", dureeDelaiApresMiseendemeure),
                                        Map.entry("libDelaiApresMiseendemeure", libDelaiApresMiseendemeure),
                                        Map.entry("lienWhatsapp", lienWhatsapp),
                                        Map.entry("lienMail", lienMail),
                                        Map.entry("dateTJ", client.getDateTrans().split(" ")[0])
                                );

                                Path output = Paths.get("reports"+client.getMatriculeClient(),
                                        "client-" + client.getMatriculeClient()+rep.getIdRepartition()+ ".pdf");

                                String template = rep.getLibMessage();

                                for (Map.Entry<String, Object> entry : vars.entrySet()) {
                                    template = template.replace("%" + entry.getKey() + "%", String.valueOf(entry.getValue()));
                                }

                                String processedTemplate = template;

                                return  this.renderAndSavePdf(templateName, vars, output)
                                        .map(path -> new ListMessage(
                                                null,
                                                processedTemplate,
                                                "client-" + client.getMatriculeClient()+rep.getIdRepartition()+ ".pdf",
                                                dateEnvoi,
                                                (index+1),
                                                false,
                                                clientOpId
                                        ));
                            });

                    // 2) Mono of ListeParametre list
                    Mono<List<ListeParametre>> listeParamMono = Flux.fromIterable(repartitionEnvoi)
                            .flatMap(rep ->
                                    variableRepartitionRepository
                                            .findAllByIdRepartition(rep.getIdRepartition())
                                            .map(vr -> new ListeParametre(
                                                    null,
                                                    vr.getLibVariable(),
                                                    String.valueOf(mappageDonnee.get(vr.getLibVariable())),
                                                    vr.getType_var(),
                                                    clientOpId
                                            ))
                            )
                            .collectList();

                    // 3) Save ListMessage & CanalMessageEnvoi
                    Mono<Void> saveMessages = listMsgFlux
                            .collectList()
                            .flatMap(listMsg ->
                                    listMessageRepository.saveAll(listMsg).collectList()
                            )
                            .flatMap(savedMsgs -> {
                                Flux<CanalMessageEnvoi> canalFlux = Flux.fromIterable(savedMsgs)
                                        .flatMap(msg ->
                                                Flux.fromIterable(operationDto.getCanalOperation())
                                                        .flatMap(co ->
                                                                canalRepository.findById(co.getIdCanal())
                                                                        .map(c -> new CanalMessageEnvoi(
                                                                                null, msg.getIdMessage(), c.getLibCanal()
                                                                        )
                                                                )
                                                        )
                                        );
                                return canalMessageEnvoiRepository.saveAll(canalFlux).then();
                            });

                    // 4) Save ListeParametre
                    Mono<Void> saveParams = listeParamMono
                            .flatMapMany(listeParametreRepository::saveAll)
                            .then();

                    // 5) Chain saves then return savedOp
                    return saveMessages
                            .then(saveParams)
                            .thenReturn(savedOp);
                })
                // 6) Map to DTO
                .map(op -> new ClientOperationDto(
                        op.getIdClientOperation(),
                        op.getIdClient(),
                        op.getIdOperation(),
                        op.getPresenceDoc(),
                        op.getDateDebut(),
                        op.getDateFin(),
                        Collections.emptyList()
                ));
    }

    public Mono<OperationDto> getCanalOperationDto(ListeOperations operations){

        Mono<List<RepartitionEnvoi>> listRepartition = repartitionEnvoiRepository.getRepartitionEnvoiByIdOperation(operations.getIdOperation()).collectList();
        Mono<List<CanalOperation>> listCanalOperation = canalOperationRepository.findByIdOperation(operations.getIdOperation()).collectList();

        listRepartition.flatMap(lo -> {
            System.out.println("Liste repartition : " + lo.size());
            return Mono.just(lo);
        });

        listCanalOperation.flatMap(lo -> {
            System.out.println("Liste repartition : " + lo.size());
            return Mono.just(lo);
        });

        return Mono.zip(listRepartition, listCanalOperation)
                .map(tuple -> {
                    List<RepartitionEnvoi> repartitions = tuple.getT1();
                    List<CanalOperation> canals = tuple.getT2();

                    return new OperationDto(
                            operations.getIdOperation(),
                            operations.getLibOp(),
                            operations.getNbreJour(),
                            repartitions,
                            canals
                    );
                });
    }

    @Override
    public Mono<Client> saveDepassementClient(Client client, ListeOperations operations) {
        return clientRepository.save(client)
                .flatMap(
                        saveClt -> getCanalOperationDto(operations)
                                .flatMap(operationDto1 ->
                                    getClientOperationDto(operationDto1, saveClt)
                                            .thenReturn(saveClt)
                                )
                );


    }

    @Override
    public Mono<Integer> sendEmailToClient() {
        return clientOperationRepository.findAll()
                .flatMap(cltOp ->
                        listMessageRepository.findByClientOperationIdAndDate(cltOp.getIdClientOperation(), LocalDate.now())
                                .flatMap(libMessage ->
                                        clientRepository.findById(cltOp.getIdClient())
                                                .flatMap(clt -> {
                                                    try {
                                                        System.out.println("Envoi des emails");
                                                        this.sendMessage(
                                                                clt.getEmailClt(),
                                                                "Rappel apurement",
                                                                libMessage.getLibMessage(),
                                                                libMessage.getLienFich(),
                                                                clt.getMatriculeClient()
                                                        );

                                                        System.out.println("Email envoyé avec succes");
                                                        System.out.println("************************");

                                                        libMessage.setMessEnv(true);

                                                        return listMessageRepository.save(libMessage)
                                                                .flatMap(msg -> {
                                                                    System.out.println("Email sauvegardé avec succes ++++++++++++++");
                                                                    System.out.println("------------------------");

                                                                    if(cltOp.getNbreEnvoi() == msg.getNumEnvoi()) {

                                                                        return clientOperationService.deleteClientOperation(cltOp.getIdClientOperation())
                                                                                .flatMap(delCltOp -> {
                                                                                    System.out.println("Client operation supprimé avec succes ++++++++++++++");
                                                                                    return plafondNonApureService.savePlafondNonApure(
                                                                                            new PlafondNonApure(
                                                                                                    null,
                                                                                                    clt.getNomClient(),
                                                                                                    clt.getPrenomClient(),
                                                                                                    cltOp.getDateDebut(),
                                                                                                    clt.getNumTel(),
                                                                                                    clt.getEmailClt(),
                                                                                                    clt.getCumulTrans(),
                                                                                                    clt.getMoisVoyage(),
                                                                                                    clt.getAnneeVoyage(),
                                                                                                    clt.getNbreJrApur()
                                                                                            )
                                                                                    );
                                                                                });
                                                                    }

                                                                    return Mono.just(msg);
                                                                })
                                                                .onErrorResume(saveErr -> {
                                                                    log.error("Erreur lors de la sauvegarde du message", saveErr);
                                                                    return Mono.empty();
                                                                });

                                                    } catch (Exception e) {
                                                        log.error("erreur d'envoi du message", e);
                                                        TabErreur tabErreur = new TabErreur(
                                                                null,
                                                                cltOp.getIdOperation(),
                                                                1,
                                                                "Erreur d'envoi de l'email : " + e.getMessage(),
                                                                clt.getMatriculeClient(),
                                                                clt.getNomClient(),
                                                                LocalDate.now(),
                                                                "NJI2"
                                                        );

                                                        tabErreurService.creerTabErreur(tabErreur);
                                                        return Mono.empty();
                                                    }
                                                })
                                )
                )
                .count()
                .map(Long::intValue); // ✅ CE Mono est maintenant retourné
    }


    public void sendMessage(String email, String objet, String message, String pieceJointe, String matClient) throws Exception {

        try {

            log.error("Liste des erreurs ----");
            log.error(email);
            log.error(objet);
            log.error(message);
            log.error(pieceJointe);
            log.error(matClient);
            log.error("Liste des erreurs ----++++");

            //String[] paramPieceJointe = pieceJointe.split("\\");

            Path filePath = Paths.get("reports"+matClient, pieceJointe);

            ///log.error(paramPieceJointe[0]);
            //log.error(paramPieceJointe[1]);

            File pdfFile = filePath.toFile();

            if (pdfFile.exists()) {
                boolean repEnvoiEmail = emailService.sendEmailWithAttachments(email, objet, message, List.of(pdfFile));
                if (!repEnvoiEmail) throw new Exception("Erreur lors de l'envoi du mail");
            } else {
                log.error("Erreur lors de l'envoi du message");
                throw new Exception("Erreur de recuperation des fichiers de pieces jointes");
            }

        } catch (Exception e) {
            log.error("Erreur lors de l'envoi du message ++++");
            log.error(e.getMessage());
            log.error("Erreur lors de l'envoi du message ----");
        }


    }


}
