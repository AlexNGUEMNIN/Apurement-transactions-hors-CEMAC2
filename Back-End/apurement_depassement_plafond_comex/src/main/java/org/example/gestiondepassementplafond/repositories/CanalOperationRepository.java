package org.example.gestiondepassementplafond.repositories;

import org.example.gestiondepassementplafond.modeles.CanalOperation;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface CanalOperationRepository extends ReactiveCrudRepository<CanalOperation, Long> {

    @Query("select * from canal_operation where id_operation = :idOperation")
    public Flux<CanalOperation> findByIdOperation(Long idOperation);

}
