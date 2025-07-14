package org.example.gestiondepassementplafond.services.listeoperation;

import org.example.gestiondepassementplafond.modeles.ListeOperations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ListeOperationService {

    Mono<ListeOperations> getOperationById(Long id);
    Flux<ListeOperations> getAllOperations();
    Mono<ListeOperations> addOperation(ListeOperations operation);
    Mono<ListeOperations> updateOperation(Long id, ListeOperations operation);
    Mono<Void> deleteOperation(Long id);
}
