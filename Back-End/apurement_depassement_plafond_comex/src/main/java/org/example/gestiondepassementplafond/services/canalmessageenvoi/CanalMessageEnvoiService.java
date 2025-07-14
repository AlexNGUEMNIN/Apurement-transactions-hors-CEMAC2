package org.example.gestiondepassementplafond.services.canalmessageenvoi;

import org.example.gestiondepassementplafond.modeles.CanalMessageEnvoi;
import org.example.gestiondepassementplafond.modeles.ListMessage;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CanalMessageEnvoiService {

    public Mono<CanalMessageEnvoi> saveCanalMessageEnvoi(CanalMessageEnvoi canalMessageEnvoi);
    public Mono<CanalMessageEnvoi> updateCanalMessageEnvoi(CanalMessageEnvoi canalMessageEnvoi);
    public Mono<CanalMessageEnvoi> getCanalMessageEnvoiById(Long id);
    public Flux<CanalMessageEnvoi> getAllCanalMessageEnvoi();
    public Mono<Void> deleteCanalMessageEnvoi(Long id);
    public Flux<CanalMessageEnvoi> getCanalMessageEnvoiByMessageId(Long messageId);

}
