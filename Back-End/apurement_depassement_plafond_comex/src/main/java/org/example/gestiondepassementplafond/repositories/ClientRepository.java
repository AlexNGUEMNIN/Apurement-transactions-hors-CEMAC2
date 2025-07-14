package org.example.gestiondepassementplafond.repositories;

import org.example.gestiondepassementplafond.modeles.Client;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface ClientRepository extends ReactiveCrudRepository<Client, Long> {
    Mono<Client> findByNomClient(String nom);

    @Query("delete from client where id_client = :idClient")
    Mono<Boolean> deleteClientByIdClient(Long idClient);
}
