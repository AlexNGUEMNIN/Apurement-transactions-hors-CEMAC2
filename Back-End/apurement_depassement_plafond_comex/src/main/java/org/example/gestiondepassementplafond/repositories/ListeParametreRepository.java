package org.example.gestiondepassementplafond.repositories;

import org.example.gestiondepassementplafond.modeles.ListeParametre;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ListeParametreRepository extends ReactiveCrudRepository<ListeParametre, Long> {

    @Query("select * from liste_parametre where id_message = :messageId")
    public Flux<ListeParametre> getListeParametreByMessageId(Long messageId);

}
