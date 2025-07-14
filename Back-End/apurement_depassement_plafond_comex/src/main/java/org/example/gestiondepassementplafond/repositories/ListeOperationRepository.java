package org.example.gestiondepassementplafond.repositories;

import org.example.gestiondepassementplafond.modeles.ListeOperations;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface ListeOperationRepository extends ReactiveCrudRepository<ListeOperations, Long> {

    @Query("DELETE FROM liste_operation WHERE id_operation = :id")
    Mono<Void> deleteOperationById(Long id);

    @Query("SELECT * FROM liste_operation WHERE id_operation = :id")
    Mono<ListeOperations> findByIdOperation(Long id);


}
