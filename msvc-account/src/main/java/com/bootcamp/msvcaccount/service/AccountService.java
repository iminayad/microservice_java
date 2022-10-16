package com.bootcamp.msvcaccount.service;

import com.bootcamp.msvcaccount.model.document.Account;
import com.specification.api.dto.AccountDto;
import com.specification.api.dto.NewAccountDto;
import com.specification.api.dto.NewProductListDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountService<T, K> {

    Mono<Account> save(Account account);

    Flux<Account> findAll();

    Mono<Account> findAccountByClientId(String clientId);

    Mono<Account> findById(String accountId);
    Mono<Account> save2(NewAccountDto newAccountDto);

}
