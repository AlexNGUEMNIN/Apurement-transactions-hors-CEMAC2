package org.example.gestiondepassementplafond.controller;


import org.example.gestiondepassementplafond.modeles.RepartitionEnvoi;
import org.example.gestiondepassementplafond.services.repartitionenvoi.RepartitionEnvoiService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/repartition")
public class RepartitionEnvoiController {

    private final RepartitionEnvoiService repartitionEnvoiService;


    public RepartitionEnvoiController(RepartitionEnvoiService repartitionEnvoiService) {
        this.repartitionEnvoiService = repartitionEnvoiService;
    }

    @PostMapping
    public Mono<RepartitionEnvoi> saveRepartitionEnvoi(RepartitionEnvoi repartitionEnvoi) {
        return repartitionEnvoiService.saveRepartitionEnvoi(repartitionEnvoi);
    }
}
