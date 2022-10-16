package com.bootcamp.msvcclient.repository;

import com.bootcamp.msvcclient.model.document.Client;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface ClientRepository extends ReactiveMongoRepository<Client, String> {

    Mono<Client> findByIdentityNumber(String identityNumber);
    Mono<Client> deleteByIdentityNumber(String identityNumber);
}
