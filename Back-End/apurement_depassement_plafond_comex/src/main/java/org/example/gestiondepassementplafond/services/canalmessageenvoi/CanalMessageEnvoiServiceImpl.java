package org.example.gestiondepassementplafond.services.canalmessageenvoi;

import org.example.gestiondepassementplafond.modeles.CanalMessageEnvoi;
import org.example.gestiondepassementplafond.repositories.CanalMessageEnvoiRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class CanalMessageEnvoiServiceImpl implements CanalMessageEnvoiService{

    private final CanalMessageEnvoiRepository canalMessageEnvoiRepository;

    public CanalMessageEnvoiServiceImpl(CanalMessageEnvoiRepository canalMessageEnvoiRepository) {
        this.canalMessageEnvoiRepository = canalMessageEnvoiRepository;
    }

    @Override
    public Mono<CanalMessageEnvoi> saveCanalMessageEnvoi(CanalMessageEnvoi canalMessageEnvoi) {
        return this.canalMessageEnvoiRepository.save(canalMessageEnvoi);
    }

    @Override
    public Mono<CanalMessageEnvoi> updateCanalMessageEnvoi(CanalMessageEnvoi canalMessageEnvoi) {
        return null;
    }

    @Override
    public Mono<CanalMessageEnvoi> getCanalMessageEnvoiById(Long id) {
        return this.canalMessageEnvoiRepository.findById(id);
    }

    @Override
    public Flux<CanalMessageEnvoi> getAllCanalMessageEnvoi() {
        return this.canalMessageEnvoiRepository.findAll();
    }

    @Override
    public Mono<Void> deleteCanalMessageEnvoi(Long id) {
        return null;
    }

    @Override
    public Flux<CanalMessageEnvoi> getCanalMessageEnvoiByMessageId(Long messageId) {
        return this.canalMessageEnvoiRepository.findByMessageId(messageId);
    }
}
