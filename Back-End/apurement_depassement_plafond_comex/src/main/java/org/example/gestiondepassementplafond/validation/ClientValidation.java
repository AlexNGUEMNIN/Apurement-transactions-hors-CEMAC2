package org.example.gestiondepassementplafond.validation;

import org.example.gestiondepassementplafond.exception.ApplicationException;
import org.example.gestiondepassementplafond.modeles.Client;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class ClientValidation {

    private static Predicate<Client> hasMatricule(){
        return dto -> Objects.nonNull(dto.getMatriculeClient());
    }

    private static UnaryOperator<Mono<Client>> validate() {
        return mono -> mono.filter(hasMatricule())
                .switchIfEmpty(ApplicationException.clientMatriculeNotFound())
                .filter(hasValidEmail())
                .switchIfEmpty(ApplicationException.missingOrInvalidEmail());
    }

    private static Predicate<Client> hasValidEmail() {
        return dto -> Objects.nonNull(dto.getEmailClt()) && dto.getEmailClt().contains("@");
    }

    public static List<String> validateClient(Client client) {
        List<String> clientError = new ArrayList<>();
        if(client.getNomClient() == null || client.getNomClient().isEmpty() || client.getNomClient().isBlank()){
            clientError.add("Le nom est incorrect veuillez l'ajouter");
        }
        return clientError;
    }

}
