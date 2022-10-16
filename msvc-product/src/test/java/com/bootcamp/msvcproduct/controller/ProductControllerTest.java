package com.bootcamp.msvcproduct.controller;

import com.bootcamp.msvcproduct.service.IProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import com.bootcamp.msvcproduct.model.document.Product;
import com.bootcamp.msvcproduct.model.document.Condition;

@ExtendWith(SpringExtension.class)
@WebFluxTest(ProductController.class)

class ProductControllerTest {

    private final String ID = "6328b2e2c62a566ee3ec9d59";
    @MockBean
    IProductService productService;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void clientsIdentityNumberGet() {


        Condition conditionProduct = Condition.builder()
                .id("1408")
                .hasMaintenanceFee(false)
                .maintenanceFee(0.00)
                .hasMonthlyTransactionLimit(true)
                .monthlyTransactionLimit(10)
                .hasDailyMonthlyTransactionLimit(false)
                .dailyMonthlyTransactionLimit(0)
                .productPerPersonLimit(1)
                .productPerBusinessLimit(1)
                .build();


        Product product = Product.builder()
                .id("6328b2e2c62a566ee3ec9d59")
                .name("Cuenta de Ahorros")
                .type("PASIVO")
                .category("DEBITO")
                .condition(conditionProduct)
                .build();

        Mockito.when(productService.findById(ID))
                .thenReturn(Mono.just(product));

        webTestClient.get().uri("/products/{identityNumber}", ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk();

    }



}