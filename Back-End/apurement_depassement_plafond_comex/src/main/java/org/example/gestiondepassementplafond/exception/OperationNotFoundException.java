package org.example.gestiondepassementplafond.exception;

import java.util.List;

public class OperationNotFoundException extends RuntimeException {

    public List<String> erreurs;

    public OperationNotFoundException(String message) {
        super(message);
    }

    public OperationNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

        public OperationNotFoundException(String message, Throwable cause, List<String> erreurs) {

        super(message, cause);
        this.erreurs = erreurs;

    }

}
