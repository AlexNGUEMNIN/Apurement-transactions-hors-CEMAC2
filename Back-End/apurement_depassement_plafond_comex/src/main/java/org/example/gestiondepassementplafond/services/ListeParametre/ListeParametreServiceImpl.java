package org.example.gestiondepassementplafond.services.ListeParametre;

import org.example.gestiondepassementplafond.modeles.ListeParametre;
import org.example.gestiondepassementplafond.repositories.ListeParametreRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ListeParametreServiceImpl implements ListeParametreService{

    private final ListeParametreRepository listeParametreRepository;

    public ListeParametreServiceImpl(ListeParametreRepository listeParametreRepository){
        this.listeParametreRepository = listeParametreRepository;
    }

    @Override
    public Mono<ListeParametre> saveListeParametre(ListeParametre listeParametre) {
        return listeParametreRepository.save(listeParametre);
    }

    @Override
    public Mono<ListeParametre> getListeParametreById(Long id) {
        return listeParametreRepository.findById(id);
    }

    @Override
    public Flux<ListeParametre> getAllListeParametre() {
        return listeParametreRepository.findAll();
    }

    @Override
    public Mono<Void> deleteListeParametre(Long id) {
        return null;
    }

    @Override
    public Flux<ListeParametre> getListeParametreByMessageId(Long messageId) {
        return listeParametreRepository.getListeParametreByMessageId(messageId);
    }
}
