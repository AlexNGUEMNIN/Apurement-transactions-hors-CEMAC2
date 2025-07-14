package org.example.gestiondepassementplafond.services.variablerepartition;

import org.example.gestiondepassementplafond.modeles.VariableRepartition;
import org.example.gestiondepassementplafond.repositories.VariableRepartitionRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class VariableRepartitionServiceImpl implements VariableRepartitionService{

    private final VariableRepartitionRepository variableRepartitionRepository;

    public VariableRepartitionServiceImpl(VariableRepartitionRepository variableRepartitionRepository) {
        this.variableRepartitionRepository = variableRepartitionRepository;
    }

    @Override
    public Mono<VariableRepartition> create(Mono<VariableRepartition> variableRepartition) {
        return null;
    }

    @Override
    public Mono<VariableRepartition> updateVariableRepartition(Mono<VariableRepartition> variableRepartition, Long id) {
        return null;
    }

    @Override
    public Mono<Boolean> deleteVariableRepartition(Long id) {
        return null;
    }

    @Override
    public Flux<VariableRepartition> findAllVariableRepartition() {
        return null;
    }

    @Override
    public Flux<VariableRepartition> getAllVariableRepartitionByRepartitionId(Long id) {
        return variableRepartitionRepository.findAllByIdRepartition(id);
    }
}
