package org.example.gestiondepassementplafond.exception;

import reactor.core.publisher.Mono;

public class ApplicationException {

    public static <T> Mono<T> clientNotFound(String mat) {
        return Mono.error(new ClientNotFoundException(mat));
    }

    public static <T> Mono<T> clientMatriculeNotFound() {
        return Mono.error(new InvalidInputException("Le matricule du Client est obligatoire"));
    }

    public static <T> Mono<T> missingOrInvalidEmail() {
        return Mono.error(new InvalidInputException("L'email du Client doit exister et etre sur un bon format"));
    }

    public static <T> Mono<T> operationNotFound(Long id) {
        return Mono.error(new InvalidInputException(id));
    }

}
