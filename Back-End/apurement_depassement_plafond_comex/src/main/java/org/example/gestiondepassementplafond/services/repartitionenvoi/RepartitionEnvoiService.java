package org.example.gestiondepassementplafond.services.repartitionenvoi;

import org.example.gestiondepassementplafond.modeles.RepartitionEnvoi;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RepartitionEnvoiService {

    public Mono<RepartitionEnvoi> saveRepartitionEnvoi(RepartitionEnvoi repartitionEnvoi);
    public Mono<RepartitionEnvoi> getRepartitionEnvoiById(Long id);
    public Mono<RepartitionEnvoi> updateRepartitionEnvoi(Long id, RepartitionEnvoi repartitionEnvoi);
    public Mono<Boolean> deleteRepartitionEnvoi(Long id);
    public Flux<RepartitionEnvoi> getRepartitionEnvoiByOperationId(Long id);

}
