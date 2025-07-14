package org.example.gestiondepassementplafond.services.plafond2;

import org.example.gestiondepassementplafond.modeles.PlafondNonApure;
import reactor.core.publisher.Mono;

public interface PlafondNonApureService {

    public Mono<PlafondNonApure> savePlafondNonApure(PlafondNonApure plafondNonApure);
    public Mono<PlafondNonApure> updatePlafondNonApure(PlafondNonApure plafondNonApure, Long id);
    public Mono<Boolean> deletePlafondNonApure(Long id);

}
