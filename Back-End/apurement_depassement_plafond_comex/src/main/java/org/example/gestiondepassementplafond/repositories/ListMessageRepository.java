package org.example.gestiondepassementplafond.repositories;

import org.example.gestiondepassementplafond.modeles.ListMessage;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

public interface ListMessageRepository extends ReactiveCrudRepository<ListMessage, Long> {

    @Query("select * from list_message where id_client_operation = :clietOpId")
    public Flux<ListMessage> getListMessageByClientOperationId(Long clietOpId);

    @Query("SELECT * FROM list_message WHERE id_client_operation = :clientOperationId AND date_envoi = :dateEnvoi")
    public Flux<ListMessage> findByClientOperationIdAndDate(Long clientOperationId, LocalDate dateEnvoi);

}
