package org.example.gestiondepassementplafond.services.taberreur;

import org.example.gestiondepassementplafond.modeles.TabErreur;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TabErreurService {

    public Mono<TabErreur> creerTabErreur(TabErreur tabErreur);
    public Mono<TabErreur> modifierTabErreur(TabErreur tabErreur, Long idTabErreur);
    public Flux<TabErreur> getAllTabErreurs();
    public Mono<TabErreur> getTabErreurById(Long idTabErreur);
    public Mono<Void> deleteTabErreurById(Long idTabErreur);

}
