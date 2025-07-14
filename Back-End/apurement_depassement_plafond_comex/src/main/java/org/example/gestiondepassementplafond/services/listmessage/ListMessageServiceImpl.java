package org.example.gestiondepassementplafond.services.listmessage;

import org.example.gestiondepassementplafond.modeles.ListMessage;
import org.example.gestiondepassementplafond.repositories.ListMessageRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ListMessageServiceImpl implements ListMessageService {

    private final ListMessageRepository listMessageRepository;

    public ListMessageServiceImpl (ListMessageRepository listMessageRepository) {
        this.listMessageRepository = listMessageRepository;
    }

    @Override
    public Mono<ListMessage> saveListMessage(ListMessage listMessage) {
        return listMessageRepository.save(listMessage);
    }

    @Override
    public Mono<ListMessage> getListMessageById(Long id) {
        return listMessageRepository.findById(id);
    }

    @Override
    public Flux<ListMessage> getAllListMessage() {
        return null;
    }

    @Override
    public Mono<Void> deleteListMessage(Long id) {
        return null;
    }

    @Override
    public Flux<ListMessage> getListMessageByClientOperationId(Long clientOpId) {
        return listMessageRepository.getListMessageByClientOperationId(clientOpId);
    }
}
