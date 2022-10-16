package com.bootcamp.msvcclient.service;

import com.bootcamp.msvcclient.model.document.Client;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ClientService {

    Flux<Client> findAll();

    Mono<Client> save(Client client);


    public Mono<Void> delete(Client client);

    Mono<Client> updateByIdentityNumber(Client client, String identityNumber);

    Mono<Client> findById(String id);
}
