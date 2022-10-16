package com.bootcamp.msvcaccount.repository;

import com.bootcamp.msvcaccount.model.document.Account;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface AccountRepository extends ReactiveMongoRepository<Account, String> {


    Mono<Account> findById (String id);
    Mono<Account> findByClientAccountId (String id);

    Mono<Account> findByProductAccountListId (String id);

}
