package com.bootcamp.msvcclient.webclient;

import com.specification.api.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class MsvcProductWebClient {

    @Value("${spring.gateway}")
    private String hostGateway;


    WebClient webclient = WebClient.create("http://localhost:8081");


    public Flux<ProductDto> productCategoryTypeClientGet(String category, String type) {
        log.info(String.format("Calling productCategoryTypeClientGet (%s)", category +" , "+ type));

    return webclient.post()
                .uri(uriBuilder ->uriBuilder
                   .path("/product/{category}/{type}/client")
                   .build(category, type))
                .retrieve()
                .bodyToFlux(ProductDto.class);
    }
}
