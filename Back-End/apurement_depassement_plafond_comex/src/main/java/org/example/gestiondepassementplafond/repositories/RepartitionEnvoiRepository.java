package org.example.gestiondepassementplafond.repositories;

import org.example.gestiondepassementplafond.modeles.RepartitionEnvoi;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RepartitionEnvoiRepository extends ReactiveCrudRepository<RepartitionEnvoi, Long> {

    @Query("select * from repartition_envoi where id_operation = :idOperation")
    public Flux<RepartitionEnvoi> getRepartitionEnvoiByIdOperation(Long idOperation);

}
