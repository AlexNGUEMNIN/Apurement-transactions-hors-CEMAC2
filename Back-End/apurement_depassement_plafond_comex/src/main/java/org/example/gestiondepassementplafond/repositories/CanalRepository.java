package org.example.gestiondepassementplafond.repositories;

import org.example.gestiondepassementplafond.modeles.Canal;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface CanalRepository extends ReactiveCrudRepository<Canal, Long> {
}
