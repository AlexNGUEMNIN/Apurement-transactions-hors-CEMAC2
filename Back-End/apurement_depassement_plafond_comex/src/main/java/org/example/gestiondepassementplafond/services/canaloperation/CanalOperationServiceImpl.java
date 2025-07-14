package org.example.gestiondepassementplafond.services.canaloperation;

import org.example.gestiondepassementplafond.modeles.CanalOperation;
import org.example.gestiondepassementplafond.modeles.RepartitionEnvoi;
import org.example.gestiondepassementplafond.repositories.CanalOperationRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class CanalOperationServiceImpl implements canaloperationservice{

    private final CanalOperationRepository canalOperationRepository;

    public CanalOperationServiceImpl(CanalOperationRepository canalOperationRepository) {
        this.canalOperationRepository = canalOperationRepository;
    }

    @Override
    public Mono<CanalOperation> saveCanalOperation(CanalOperation canalOperation) {
        return null;
    }

    @Override
    public Mono<RepartitionEnvoi> getCanalOperationById(Long idCanal) {
        return null;
    }

    @Override
    public Mono<CanalOperation> updateCanalOperation(CanalOperation canalOperation) {
        return null;
    }

    @Override
    public Mono<Boolean> deleteCanalOperation(Long idCanal) {
        return null;
    }

    @Override
    public Flux<CanalOperation> getCanalOperationByIdOperation(Long idOperation) {
        return canalOperationRepository.findByIdOperation(idOperation);
    }
}
