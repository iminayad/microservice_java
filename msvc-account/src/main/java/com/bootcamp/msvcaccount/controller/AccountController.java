package com.bootcamp.msvcaccount.controller;

import com.bootcamp.msvcaccount.model.mapper.AccountMapper;
import com.bootcamp.msvcaccount.service.AccountBusinessService;
import com.bootcamp.msvcaccount.service.AccountService;
import com.specification.api.AccountApi;
import com.specification.api.dto.AccountDto;
import com.specification.api.dto.BalanceDto;
import com.specification.api.dto.NewAccountDto;
import com.specification.api.dto.NewProductListDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
public class AccountController implements AccountApi {

    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountBusinessService accountBusinessService;


    @Autowired
    private AccountMapper accountMapper;

    @Override
    public Mono<ResponseEntity<AccountDto>> addAccount
            (Mono<NewAccountDto> newAccountDto, ServerWebExchange exchange) {
        return newAccountDto.flatMap(accountDto -> accountBusinessService.createAccount(accountDto))
                .map(account ->
                        ResponseEntity
                                .created(URI.create("/account/".concat(account.getId())))
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(accountMapper.toAccountDto(account)));
    }

    @Override
    public Mono<ResponseEntity<Flux<AccountDto>>> findAccounts(ServerWebExchange exchange) {
        return Mono.just(ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(accountBusinessService.getAccounts()));

    }

    @Override
    public Mono<ResponseEntity<AccountDto>> accountClientIdGet(String id, ServerWebExchange exchange) {
        return accountBusinessService.getClientAccountById(id).map(account -> ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(accountMapper.toAccountDto(account))
        ).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Override
    public Mono<ResponseEntity<AccountDto>> accountAccountIdProductsPost(String accountId, Mono<NewProductListDto> newProductListDto, ServerWebExchange exchange) {
        return newProductListDto.flatMap(newProductList ->
                        accountBusinessService.addProductToAccount(accountId, newProductList))
                .map(account -> ResponseEntity.created(
                                URI.create("/account/".concat(account.getId().concat("/products"))))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(accountMapper.toAccountDto(account)));
    }

    @Override
    public Mono<ResponseEntity<AccountDto>> accountAccountIdProductIdBalancePost(String accountId, String productId, Mono<BalanceDto> balanceDto, ServerWebExchange exchange) {
        return balanceDto.flatMap(balance ->
                accountBusinessService.updateBalance(accountId, productId, balance.getBalance(), balance.getBalancespent())
        ).map(account ->
                ResponseEntity
                        .created(URI.create("/account/".concat(account.getId())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(accountMapper.toAccountDto(account)));
    }

    @Override
    public Mono<ResponseEntity<AccountDto>> accountAccountIdGet(String accountId, ServerWebExchange exchange) {
        return accountBusinessService.getById(accountId).map(account -> ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(accountMapper.toAccountDto(account))
        ).defaultIfEmpty(ResponseEntity.notFound().build());
    }

}
