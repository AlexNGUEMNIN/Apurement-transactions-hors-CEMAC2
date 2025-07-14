package org.example.gestiondepassementplafond.services.canal;

import org.example.gestiondepassementplafond.modeles.Canal;
import org.example.gestiondepassementplafond.repositories.CanalRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service("firstcanalservice")
public class CanalServiceImpl implements CanalService {

    private final CanalRepository canalRepository;

    public CanalServiceImpl(final CanalRepository canalRepository) {
        this.canalRepository = canalRepository;
    }

    @Override
    public Mono<Canal> saveCanal(Canal canal) {
        return canalRepository.save(canal);
    }

    @Override
    public Mono<Canal> updateCanal(Canal canal, Long id) {
        return canalRepository.findById(id)
                .flatMap(c -> {
                    c.setLibCanal(canal.getLibCanal());
                    return canalRepository.save(c)
                            .flatMap(canal1 -> Mono.just(canal1));
                });
    }

    @Override
    public Mono<Void> deleteCanal(Canal canal) {
        return canalRepository.delete(canal);
    }

    @Override
    public Flux<Canal> getCanals() {
        return canalRepository.findAll();
    }

    @Override
    public Mono<Canal> getCanalById(Long id) {
        return canalRepository.findById(id);
    }
}
