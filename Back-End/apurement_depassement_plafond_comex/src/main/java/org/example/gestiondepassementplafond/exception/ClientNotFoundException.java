package org.example.gestiondepassementplafond.exception;

public class ClientNotFoundException extends RuntimeException{

    public static final String MESSAGE = "Client avec le matricule [mat = %s] est introuvable dans le systeme";

    public ClientNotFoundException(String mat) {
        super(MESSAGE.formatted(mat));
    }
}
