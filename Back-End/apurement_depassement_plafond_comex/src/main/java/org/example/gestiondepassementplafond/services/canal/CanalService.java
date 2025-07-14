package org.example.gestiondepassementplafond.services.canal;

import org.example.gestiondepassementplafond.modeles.Canal;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CanalService {

    public Mono<Canal> saveCanal(Canal canal);
    public Mono<Canal> updateCanal(Canal canal, Long id);
    public Mono<Void> deleteCanal(Canal canal);
    public Flux<Canal> getCanals();
    public Mono<Canal> getCanalById(Long id);
}
