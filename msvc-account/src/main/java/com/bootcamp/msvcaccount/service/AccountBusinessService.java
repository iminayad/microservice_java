package com.bootcamp.msvcaccount.service;

import com.bootcamp.msvcaccount.model.document.Account;
import com.specification.api.dto.AccountDto;
import com.specification.api.dto.NewAccountDto;
import com.specification.api.dto.NewProductListDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountBusinessService extends AccountService<NewAccountDto, String> {

    Mono<Account> createAccount(NewAccountDto newAccountDto);

    Flux<AccountDto> getAccounts();

    Mono<Account> getClientAccountById(String clientId);

    Mono<Account> addProductToAccount(String accountId, NewProductListDto newProductListDto);

    Mono<Account> updateBalance(String accountId, String productId, Double balance, Double balanceSpent);

    Mono <Account> getById(String accountId);
}
