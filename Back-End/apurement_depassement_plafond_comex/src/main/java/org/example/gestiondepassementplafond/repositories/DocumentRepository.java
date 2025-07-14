package org.example.gestiondepassementplafond.repositories;

import org.example.gestiondepassementplafond.modeles.Documents;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface DocumentRepository extends ReactiveCrudRepository<Documents, Long> {
}
