package org.example.gestiondepassementplafond.services.client;

import org.example.gestiondepassementplafond.modeles.Client;
import org.example.gestiondepassementplafond.modeles.ListeOperations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

public interface ClientService {
    public Mono<Client> createClient(Client client);
    public Flux<Client> getAllClient();
    public Mono<Boolean> deleteClient(Long clientId);
    public Mono<Client> updateClient(Long clientId, Client clientDto);
    public Mono<Client> getClientById(Long clientId);
    public Mono<Client> saveDepassementClient(Client client, ListeOperations operations);
    public Mono<Integer> sendEmailToClient();

}
