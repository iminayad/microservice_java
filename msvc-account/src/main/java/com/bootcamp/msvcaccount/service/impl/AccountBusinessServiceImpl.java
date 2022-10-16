package com.bootcamp.msvcaccount.service.impl;

import com.bootcamp.msvcaccount.model.document.Account;
import com.bootcamp.msvcaccount.model.mapper.AccountMapper;
import com.bootcamp.msvcaccount.repository.AccountRepository;
import com.bootcamp.msvcaccount.service.AccountBusinessService;
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

@Service
public class AccountBusinessServiceImpl extends AccountServiceImpl<NewAccountDto, String> implements AccountBusinessService {

    Logger logger = LoggerFactory.getLogger(AccountBusinessServiceImpl.class);

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private MsvcProductWebClient productWebClient;
    @Autowired
    private MsvcClientWebClient clientWebClient;


    @Override
    public Flux<AccountDto> getAccounts() {
        return this.findAll()
                .map(account -> {
                    logger.info(account.getClientAccount().getId());
                    logger.info(account.getClientAccount().getName());
                    return accountMapper.toAccountDto(account);
                })
                .switchIfEmpty(Mono.error(
                        new Throwable("No hay cuentas registradas")));
    }

    @Override
    public Mono<Account> createAccount(NewAccountDto newAccountDto) {

        return productWebClient.getListProductByWebClient(newAccountDto.getProductList())
                .flatMap(productList ->
                        clientWebClient.getClientByWebClient(newAccountDto.getClient())
                                .flatMap(ClientAccountDto ->
                                        this.save(new Account(productList, ClientAccountDto))));
    }


    @Override
    public Mono<Account> getClientAccountById(String clientId) {
        return  accountRepository.findByClientAccountId(clientId)
                .switchIfEmpty(
                        Mono.error(new Throwable("No existe esta cuenta")));
    }

    @Override
    public Mono<Account> addProductToAccount(String accountId, NewProductListDto newProductListDto) {
        return accountRepository.findById(accountId)
                .switchIfEmpty(
                        Mono.error(new Throwable("No está registrada esta cuenta."))
                )
                .flatMap(account -> {
                    logger.info("Si esta registrada la cuenta");
                    logger.info(account.getClientAccount().getName());
                    logger.info(account.getClientAccount().getCustomerType());
                    return Flux.fromIterable(newProductListDto.getProductList())
                            .flatMap(newProductAccountDto ->
                                    {
                                        logger.info("El nuevo producto a registrar es: ");
                                        logger.info(newProductAccountDto.getId());
                                        return productWebClient.getProduct(newProductAccountDto.getId());
                                    }
                            )
                            .map(productDtos -> accountMapper.toProductAccount(productDtos))
                            .collectList()
                            .switchIfEmpty(
                                    Mono.error(new Throwable("Ya tiene el mismo producto"))
                            )
                            .flatMap(productAccountDtos -> accountRepository.save(
                                    new Account(productAccountDtos,
                                            account.getClientAccount()
                                    ))
                            );
                });
    }

    @Override
    public Mono<Account> getById(String accountId) {
        return this.findById(accountId)
                .switchIfEmpty(
                        Mono.error(new Throwable("Este cliente no tiene cuentas")));
    }

    @Override
    public Mono<Account> updateBalance(String accountId, String productId, Double balance, Double balanceSpent) {
        return accountRepository.findById(accountId).
                flatMap(account ->
                        Mono.just(account.getProductAccountList()
                                        .stream()
                                        .map(productAccountDto ->
                                                {
                                                    if (productAccountDto.getId().equals(productId)) {

                                                        productAccountDto.setBalance(balance);
                                                        if(balanceSpent!=null){
                                                            productAccountDto.setBalancespent(balanceSpent);
                                                        }
                                                    }
                                                    return productAccountDto;
                                                }
                                        ).collect(Collectors.toList()))
                                .flatMap(productAccountList -> {
                                            account.setProductAccountList(productAccountList);
                                            return this.save(account);
                                        }
                                )
                );
    }

}
