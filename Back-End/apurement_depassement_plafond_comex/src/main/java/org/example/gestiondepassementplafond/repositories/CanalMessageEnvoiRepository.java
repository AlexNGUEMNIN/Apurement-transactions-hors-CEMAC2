package org.example.gestiondepassementplafond.repositories;

import org.example.gestiondepassementplafond.modeles.CanalMessageEnvoi;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface CanalMessageEnvoiRepository extends ReactiveCrudRepository<CanalMessageEnvoi, Long> {

    @Query("select * from canal_message_envoi where id_message = :messageId")
    Flux<CanalMessageEnvoi> findByMessageId(Long messageId);

}
