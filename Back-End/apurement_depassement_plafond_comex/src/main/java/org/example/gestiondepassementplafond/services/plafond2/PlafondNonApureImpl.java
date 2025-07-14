package org.example.gestiondepassementplafond.services.plafond2;

import org.example.gestiondepassementplafond.modeles.PlafondNonApure;
import org.example.gestiondepassementplafond.repositories.PlafondNonApureRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service("plafondnonapure1")
public class PlafondNonApureImpl implements PlafondNonApureService{

    private final PlafondNonApureRepository plafondNonApureRepository;

    public PlafondNonApureImpl(PlafondNonApureRepository plafondNonApureRepository) {
        this.plafondNonApureRepository = plafondNonApureRepository;
    }

    @Override
    public Mono<PlafondNonApure> savePlafondNonApure(PlafondNonApure plafondNonApure) {
        return plafondNonApureRepository.save(plafondNonApure);
    }

    @Override
    public Mono<PlafondNonApure> updatePlafondNonApure(PlafondNonApure plafondNonApure, Long id) {
        return null;
    }

    @Override
    public Mono<Boolean> deletePlafondNonApure(Long id) {
        return null;
    }
}
