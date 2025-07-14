package org.example.gestiondepassementplafond.exception;

public class InvalidInputException extends RuntimeException {

    public InvalidInputException(String message) {
        super(message);
    }

    public InvalidInputException(Long id) {
        super(String.format("L'Operation ayant pour identifiant %d est introuvable", id));
    }

}
