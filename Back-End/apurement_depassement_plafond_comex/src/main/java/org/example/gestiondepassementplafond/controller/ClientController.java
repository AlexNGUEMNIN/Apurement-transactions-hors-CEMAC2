package org.example.gestiondepassementplafond.controller;

import org.example.gestiondepassementplafond.services.client.ClientService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("client")
public class ClientController {

    public final ClientService clientService;

    public ClientController(@Qualifier("clientImpl1") ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping(value = "/sendEmail")
    public Mono<Integer> sendEmailOnDate(){
        System.out.println("Envoie des emails");
        return clientService.sendEmailToClient();
    }

}
