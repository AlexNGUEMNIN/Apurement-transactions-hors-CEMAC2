package org.example.gestiondepassementplafond.repositories;

import org.example.gestiondepassementplafond.modeles.Message;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface MessageRepository extends ReactiveCrudRepository<Message, Long> {
}
