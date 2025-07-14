package org.example.gestiondepassementplafond.services.canaloperation;

import org.example.gestiondepassementplafond.modeles.CanalOperation;
import org.example.gestiondepassementplafond.modeles.RepartitionEnvoi;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface canaloperationservice {

    public Mono<CanalOperation> saveCanalOperation(CanalOperation canalOperation);
    public Mono<RepartitionEnvoi> getCanalOperationById(Long idCanal);
    public Mono<CanalOperation> updateCanalOperation(CanalOperation canalOperation);
    public Mono<Boolean> deleteCanalOperation(Long idCanal);
    public Flux<CanalOperation> getCanalOperationByIdOperation(Long idOperation);

}
