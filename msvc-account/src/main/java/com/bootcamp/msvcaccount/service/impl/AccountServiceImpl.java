package com.bootcamp.msvcaccount.service.impl;

import com.bootcamp.msvcaccount.model.document.Account;
import com.bootcamp.msvcaccount.model.mapper.AccountMapper;
import com.bootcamp.msvcaccount.repository.AccountRepository;
import com.bootcamp.msvcaccount.service.AccountService;
import com.bootcamp.msvcaccount.webclient.MsvcClientWebClient;
import com.bootcamp.msvcaccount.webclient.MsvcProductWebClient;
import com.specification.api.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

public abstract class AccountServiceImpl<T, K> implements AccountService<T, K> {

    Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);
    @Autowired
    private MsvcProductWebClient productWebClient;
    @Autowired
    private MsvcClientWebClient clientWebClient;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountMapper accountMapper;


    @Override
    public Flux<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public Mono<Account> save(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public Mono<Account> findById(String accountId) {
        return accountRepository.findById(accountId);
    }

    @Override
    public Mono<Account> findAccountByClientId(String clientId) {
        return accountRepository.findByClientAccountId(clientId);
    }

    @Override
    public Mono<Account> save2(NewAccountDto newAccountDto) {

        return accountRepository.findByClientAccountId(newAccountDto.getClient().getId())
                .switchIfEmpty(
                        Mono.error(new Throwable("El cliente no tiene una cuenta"))
                ).flatMap(account ->
                        Flux.fromIterable(newAccountDto.getProductList())
                                .flatMap(newProductAccountDto ->
                                        productWebClient.getProduct(newProductAccountDto.getId()))
                                .collectList()
                                .flatMap(productList -> clientWebClient.getClientById(newAccountDto.getClient().getId())
                                        .flatMap(clientDto -> accountRepository.save(
                                                new Account(productList
                                                        .stream()
                                                        .map(accountMapper::toProductAccount)
                                                        .collect(Collectors.toList()),
                                                        accountMapper.toClientAccount(clientDto)
                                                )))))
                .flatMap(account ->
                        Flux.fromIterable(newAccountDto.getProductList())
                                .flatMap(newProductAccountDto ->
                                        productWebClient.getProduct(newProductAccountDto.getId()))
                                .collectList()
                                .flatMap(
                                        productList -> Flux.fromIterable(account.getProductAccountList())
                                                .filter(productAccountDto -> productList.stream()
                                                        .allMatch(t -> t.getId().equals(productAccountDto.getId()))
                                                ).collect(Collectors.toList()))
                ).switchIfEmpty(
                        Mono.error(new Throwable("Ya tiene el mismo producto"))
                )
                .flatMap(productListFilter -> clientWebClient.getClientById(newAccountDto.getClient().getId())
                        .flatMap(clientDto -> accountRepository.save(
                                new Account(productListFilter,
                                        accountMapper.toClientAccount(clientDto)
                                ))));

    }


}
