package org.example.gestiondepassementplafond.controller;

import org.example.gestiondepassementplafond.exception.InvalidInputException;
import org.example.gestiondepassementplafond.modeles.ListeOperations;
import org.example.gestiondepassementplafond.services.listeoperation.ListeOperationService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/operation")
public class ListOperationController {

    private final ListeOperationService listeOperationService;

    public ListOperationController(@Qualifier("listoperation1") ListeOperationService listeOperationService) {
        this.listeOperationService = listeOperationService;
    }

    @PostMapping
    public Mono<ListeOperations> saveListeOperations(@RequestBody ListeOperations listeOperations) {

        return listeOperationService.addOperation(listeOperations);

    }

    @PutMapping("/update/{id}")
    public Mono<ResponseEntity<ListeOperations>> updateListeOperations(@PathVariable Long id, @RequestBody ListeOperations listeOperations) {
        return listeOperationService.updateOperation(id,listeOperations)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Object>> deleteListeOperations(@PathVariable Long id) {
        return listeOperationService.deleteOperation(id)
                .then(Mono.just(ResponseEntity.noContent().build()))
                .onErrorResume(InvalidInputException.class,
                        e -> Mono.just(ResponseEntity.noContent().build()));

    }

}
