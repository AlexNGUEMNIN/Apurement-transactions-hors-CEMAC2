package org.example.gestiondepassementplafond.repositories;

import org.example.gestiondepassementplafond.modeles.VariableRepartition;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface VariableRepartitionRepository extends ReactiveCrudRepository<VariableRepartition, Long> {

    @Query("SELECT * FROM variable_repartition WHERE id_repartition = :idRepartition")
    public Flux<VariableRepartition> findAllByIdRepartition(Long idRepartition);

}
