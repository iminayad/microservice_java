package com.bootcamp.msvcclient.controller;

import com.bootcamp.msvcclient.model.document.Client;
import com.bootcamp.msvcclient.service.ClientService;
import com.specification.api.dto.ClientDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@WebFluxTest(ClientController.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class ClientControllerTest {

    private final String ID = "6328b2e2c62a566ee3ec9d59";
    @MockBean
    ClientService clientService;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void clientsIdentityNumberGet() {

        Client client = Client.builder()
                .id("6328b2e2c62a566ee3ec9d59")
                .name("Juan")
                .identityType("DNI")
                .identityNumber("45533232")
                .customerType("PERSONAL")
                .address("Peru")
                .phoneNumber("998756656")
                .build();

        Mockito.when(clientService.findById(ID))
                .thenReturn(Mono.just(client));

        webTestClient.get().uri("/clients/{identityNumber}", ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk();

    }
}