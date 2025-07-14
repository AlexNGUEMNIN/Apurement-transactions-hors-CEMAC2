package org.example.gestiondepassementplafond.services.ListeParametre;

import org.example.gestiondepassementplafond.modeles.ListeParametre;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ListeParametreService {

    public Mono<ListeParametre> saveListeParametre(ListeParametre listeParametre);
    public Mono<ListeParametre> getListeParametreById(Long id);
    public Flux<ListeParametre> getAllListeParametre();
    public Mono<Void> deleteListeParametre(Long id);
    public Flux<ListeParametre> getListeParametreByMessageId(Long messageId);

}
