package com.bootcamp.msvcaccount.webclient;

import com.bootcamp.msvcaccount.model.mapper.AccountMapper;
import com.specification.api.dto.NewProductAccountDto;
import com.specification.api.dto.NewProductDto;
import com.specification.api.dto.ProductAccountDto;
import com.specification.api.dto.ProductDto;
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
public class MsvcProductWebClient {

    Logger logger = LoggerFactory.getLogger(MsvcProductWebClient.class);

    WebClient webclient = WebClient.create("http://localhost:8081");
    @Autowired
    private AccountMapper accountMapper;

    public Mono<ProductDto> getProduct(String id) {
        logger.info(String.format("Calling getProduct (%s)", id));

        return webclient.get()
                .uri("/product/{id}", id)
                .retrieve()
                .bodyToMono(ProductDto.class);
    }

    public Mono<ProductDto> postProduct(NewProductDto newProductDto) {
        logger.info(String.format("Calling postProduct (%s)", newProductDto));

        return webclient.post()
                .uri("/product")
                .body(Mono.just(newProductDto), NewProductDto.class)
                .retrieve()
                .bodyToMono(ProductDto.class);
    }

    /**
     * Call msvc-product to get List products
     *
     * @param productAccountList - List product Account
     * @return Mono<List < ProductAccountDto>>
     */
    public Mono<List<ProductAccountDto>> getListProductByWebClient(List<NewProductAccountDto> productAccountList) {
        return Flux.fromIterable(productAccountList)
                .flatMap(newProductAccountDto ->
                        this.getProduct(newProductAccountDto.getId()))
                .collectList()
                .flatMap(productList -> Mono.just(productList
                        .stream()
                        .map(accountMapper::toProductAccount)
                        .collect(Collectors.toList()))
                );
    }

}
