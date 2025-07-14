package org.example.gestiondepassementplafond.repositories;

import org.example.gestiondepassementplafond.modeles.ClientOperations;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface ClientOperationRepository extends ReactiveCrudRepository<ClientOperations, Long> {
}
