package org.example.gestiondepassementplafond.services.variablerepartition;

import org.example.gestiondepassementplafond.modeles.VariableRepartition;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface VariableRepartitionService {

    Mono<VariableRepartition> create(Mono<VariableRepartition> variableRepartition);
    Mono<VariableRepartition> updateVariableRepartition(Mono<VariableRepartition> variableRepartition, Long id);
    Mono<Boolean> deleteVariableRepartition(Long id);
    Flux<VariableRepartition> findAllVariableRepartition();
    Flux<VariableRepartition> getAllVariableRepartitionByRepartitionId(Long id);

}
