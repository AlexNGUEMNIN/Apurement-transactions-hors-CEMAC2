package org.example.gestiondepassementplafond.repositories;

import org.example.gestiondepassementplafond.modeles.EnvMessage;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface EnvMessageRepository extends ReactiveCrudRepository<EnvMessage, Long> {
}
