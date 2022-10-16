package com.bootcamp.msvcoperation.webclient;

import com.specification.api.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.ws.rs.core.UriBuilder;

@Service
@Slf4j
public class MsvcAccountWebClient {

    @Value("${spring.gateway}")
    private String hostGateway;


    WebClient webclient = WebClient.create("http://localhost:8081");


    public Mono<AccountDto> getAccountById(String id) {
        log.info(String.format("Calling getAccountById (%s)", id));

        return webclient.get()
                .uri("/account/{id}", id)
                .retrieve()
                .bodyToMono(AccountDto.class);
    }

    public Mono<AccountDto> postAccount(NewAccountDto newAccountDto) {
        log.info(String.format("Calling postAccount (%s)", newAccountDto));

        return webclient.post()
                .uri("/account")
                .body(Mono.just(newAccountDto), NewClientDto.class)
                .retrieve()
                .bodyToMono(AccountDto.class);
    }
    public Mono<AccountDto> updateAccount(String accountId, String productId,Mono<BalanceDto> balanceDto) {
        log.info(String.format("Calling updateAccount (%s)", balanceDto));

    return webclient.post()
                .uri(uriBuilder ->uriBuilder
                 .path("/account/{accountId}/{productId}/balance")
                  .build(accountId, productId))
                .body(Mono.just(balanceDto), BalanceDto.class)
                .retrieve()
                .bodyToMono(AccountDto.class);
    }
}
