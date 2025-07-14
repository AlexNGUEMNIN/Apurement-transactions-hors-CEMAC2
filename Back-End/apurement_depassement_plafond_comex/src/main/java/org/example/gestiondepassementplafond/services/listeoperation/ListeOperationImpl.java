package org.example.gestiondepassementplafond.services.listeoperation;

import org.example.gestiondepassementplafond.exception.ApplicationException;
import org.example.gestiondepassementplafond.exception.InvalidInputException;
import org.example.gestiondepassementplafond.modeles.ListeOperations;
import org.example.gestiondepassementplafond.repositories.ListeOperationRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service("listoperation1")
public class ListeOperationImpl implements ListeOperationService {

    private final ListeOperationRepository listeOperationRepository;

    public ListeOperationImpl(ListeOperationRepository listeOperationRepository) {
        this.listeOperationRepository = listeOperationRepository;
    }

    @Override
    public Mono<ListeOperations> getOperationById(Long id) {
        return listeOperationRepository.findByIdOperation(id);
    }

    @Override
    public Flux<ListeOperations> getAllOperations() {
        return listeOperationRepository.findAll();
    }

    @Override
    public Mono<ListeOperations> addOperation(ListeOperations operation) {
        return listeOperationRepository.save(operation);
    }

    @Override
    public Mono<ListeOperations> updateOperation(Long id, ListeOperations operation) {
        return listeOperationRepository.findById(id)
                .flatMap(listOp -> {
                    listOp.setNbreJour(operation.getNbreJour());
                    listOp.setLibOp(operation.getLibOp());
                    return Mono.just(listOp);
                })
                .flatMap(listeOperationRepository::save);
    }

    @Override
    public Mono<Void> deleteOperation(Long id) {

        return listeOperationRepository.findById(id)
                .switchIfEmpty(Mono.error(new InvalidInputException(id)))
                .flatMap(listeOperationRepository::delete);
    }


}
