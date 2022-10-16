package com.bootcamp.msvcaccount.webclient;

import com.bootcamp.msvcaccount.model.mapper.AccountMapper;
import com.specification.api.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MsvcClientWebClient {

    Logger logger = LoggerFactory.getLogger(MsvcProductWebClient.class);

    WebClient webclient = WebClient.create("http://localhost:8081");
    @Autowired
    private AccountMapper accountMapper;

    public Mono<ClientDto> getClientById(String id) {
        logger.info(String.format("Calling getClient (%s)", id));

        return webclient.get()
                .uri("/client/personal/{id}", id)
                .retrieve()
                .bodyToMono(ClientDto.class);
    }

    public Mono<ClientDto> postClient(NewClientDto newClientDto) {
        logger.info(String.format("Calling postClient (%s)", newClientDto));

        return webclient.post()
                .uri("/client/personal")
                .body(Mono.just(newClientDto), NewClientDto.class)
                .retrieve()
                .bodyToMono(ClientDto.class);
    }

    /**
     * Call msvc-client to get Client
     *
     * @param client - Client
     * @return Mono<List < ProductAccountDto>>
     */
    public Mono<ClientAccountDto> getClientByWebClient(NewAccountDtoAllOfClient client) {
        return this.getClientById(client.getId())
                .flatMap(clientDto -> Mono.just(accountMapper.toClientAccount(clientDto)));
    }

}
