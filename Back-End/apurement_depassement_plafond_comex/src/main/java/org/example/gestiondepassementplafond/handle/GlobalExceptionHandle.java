package org.example.gestiondepassementplafond.handle;

import org.example.gestiondepassementplafond.exception.OperationNotFoundException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandle {

    @ExceptionHandler(OperationNotFoundException.class)
    public Mono<ResponseEntity<Map<String, Object>>> handleOperationNotFoundException(OperationNotFoundException e) {
        Map<String, Object> error = new HashMap<>();
        error.put("error", e.getMessage());
        error.put("listeErreur", e.erreurs);
        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(error));
    }

    @ExceptionHandler(WebExchangeBindException.class)
    public Mono<ResponseEntity<Map<String, Object>>> handleValidationException(WebExchangeBindException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("error", "Validation failed");
        error.put("details", ex.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList());
        return Mono.just(ResponseEntity.badRequest().body(error));
    }

}
