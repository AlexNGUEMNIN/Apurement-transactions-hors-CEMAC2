package org.example.gestiondepassementplafond.services.taberreur;

import org.example.gestiondepassementplafond.modeles.TabErreur;
import org.example.gestiondepassementplafond.repositories.TabErreurRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service("taberreur1")
public class TabErreurServiceImpl implements TabErreurService {

    public final TabErreurRepository tabErreurRepository;

    public TabErreurServiceImpl(TabErreurRepository tabErreurRepository) {
        this.tabErreurRepository = tabErreurRepository;
    }

    @Override
    public Mono<TabErreur> creerTabErreur(TabErreur tabErreur) {
        return tabErreurRepository.save(tabErreur);
    }

    @Override
    public Mono<TabErreur> modifierTabErreur(TabErreur tabErreur, Long idTabErreur) {
        return null;
    }

    @Override
    public Flux<TabErreur> getAllTabErreurs() {
        return null;
    }

    @Override
    public Mono<TabErreur> getTabErreurById(Long idTabErreur) {
        return null;
    }

    @Override
    public Mono<Void> deleteTabErreurById(Long idTabErreur) {
        return null;
    }
}
