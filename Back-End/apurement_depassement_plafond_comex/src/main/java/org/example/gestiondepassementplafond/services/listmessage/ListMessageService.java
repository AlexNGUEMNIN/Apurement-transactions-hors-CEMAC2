package org.example.gestiondepassementplafond.services.listmessage;

import org.example.gestiondepassementplafond.modeles.ListMessage;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ListMessageService {
    public Mono<ListMessage> saveListMessage(ListMessage listMessage);
    public Mono<ListMessage> getListMessageById(Long id);
    public Flux<ListMessage> getAllListMessage();
    public Mono<Void> deleteListMessage(Long id);
    public Flux<ListMessage> getListMessageByClientOperationId(Long clientOpId);
}
