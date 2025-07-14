package org.example.gestiondepassementplafond.services.repartitionenvoi;

import org.example.gestiondepassementplafond.modeles.RepartitionEnvoi;
import org.example.gestiondepassementplafond.repositories.RepartitionEnvoiRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class RepartitionEnvoiServiceImpl implements RepartitionEnvoiService{

    private final RepartitionEnvoiRepository repartitionEnvoiRepository;

    public RepartitionEnvoiServiceImpl(RepartitionEnvoiRepository repartitionEnvoiRepository){
        this.repartitionEnvoiRepository = repartitionEnvoiRepository;
    }

    @Override
    public Mono<RepartitionEnvoi> saveRepartitionEnvoi(RepartitionEnvoi repartitionEnvoi) {

        if(repartitionEnvoi == null) {
            repartitionEnvoiRepository.save(repartitionEnvoi);
        }
        return Mono.empty();
    }

    @Override
    public Mono<RepartitionEnvoi> getRepartitionEnvoiById(Long id) {
        return repartitionEnvoiRepository.findById(id);
    }

    @Override
    public Mono<RepartitionEnvoi> updateRepartitionEnvoi(Long id, RepartitionEnvoi repartitionEnvoi) {
        return repartitionEnvoiRepository.findById(id)
                .flatMap(rep -> {
                    rep.setIdOperation(repartitionEnvoi.getIdOperation());
                    rep.setComment(repartitionEnvoi.getComment());
                    rep.setRatio(repartitionEnvoi.getRatio());
                    rep.setModelFichier(repartitionEnvoi.getModelFichier());
                    rep.setLibMessage(repartitionEnvoi.getLibMessage());
                    rep.setPresenceFic(repartitionEnvoi.getPresenceFic());

                    return Mono.just(rep);
                })
                .flatMap(repartitionEnvoiRepository::save);
        //return null;
    }

    @Override
    public Mono<Boolean> deleteRepartitionEnvoi(Long id) {
        return null;
    }

    @Override
    public Flux<RepartitionEnvoi> getRepartitionEnvoiByOperationId(Long id) {
        return repartitionEnvoiRepository.getRepartitionEnvoiByIdOperation(id);
    }
}
