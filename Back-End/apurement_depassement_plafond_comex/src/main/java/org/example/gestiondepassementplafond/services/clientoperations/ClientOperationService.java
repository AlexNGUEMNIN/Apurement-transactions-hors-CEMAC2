package org.example.gestiondepassementplafond.services.clientoperations;

import org.example.gestiondepassementplafond.modeles.ClientOperations;
import org.example.gestiondepassementplafond.modeles.RepartitionEnvoi;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface ClientOperationService {

    public Mono<ClientOperations> saveClientOperation(ClientOperations clientOperations);
    public Mono<ClientOperations> updateClientOperation(ClientOperations clientOperations);
    public Mono<Boolean> deleteClientOperation(Long id);
    public Mono<ClientOperations> getClientOperationById(Long id);

}
