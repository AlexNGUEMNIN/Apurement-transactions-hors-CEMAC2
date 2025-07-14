package org.example.gestiondepassementplafond.services.clientoperations;

import org.example.gestiondepassementplafond.modeles.ClientOperations;
import org.example.gestiondepassementplafond.repositories.ClientOperationRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service("clientoperation1")
public class ClientOperationServImpl implements ClientOperationService{

    private final ClientOperationRepository clientOperationRepository;

    public ClientOperationServImpl(ClientOperationRepository clientOperationRepository) {
        this.clientOperationRepository = clientOperationRepository;
    }

    @Override
    public Mono<ClientOperations> saveClientOperation(ClientOperations clientOperations) {
        return clientOperationRepository.save(clientOperations);
    }

    @Override
    public Mono<ClientOperations> updateClientOperation(ClientOperations clientOperations) {
        return null;
    }

    @Override
    public Mono<Boolean> deleteClientOperation(Long id) {
        return null;
    }

    @Override
    public Mono<ClientOperations> getClientOperationById(Long id) {
        return clientOperationRepository.findById(id);
    }
}
