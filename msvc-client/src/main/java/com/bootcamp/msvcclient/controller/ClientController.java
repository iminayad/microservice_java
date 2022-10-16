package com.bootcamp.msvcclient.controller;

import com.bootcamp.msvcclient.model.mapper.ClientMapper;
import com.bootcamp.msvcclient.service.ClientService;
import com.specification.api.ClientApi;
import com.specification.api.dto.ClientBusinessDto;
import com.specification.api.dto.ClientDto;
import com.specification.api.dto.NewClientBusinessDto;
import com.specification.api.dto.NewClientDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
public class ClientController implements ClientApi {

    @Autowired
    private ClientMapper mapper;
    @Autowired
    private ClientService clientService;

    @Override
    public Mono<ResponseEntity<ClientBusinessDto>> addClientBusiness(Mono<NewClientBusinessDto> newClientBusinessDto, ServerWebExchange exchange) {
        return newClientBusinessDto.flatMap(clientDto -> clientService.save(mapper.toModelBusiness(clientDto)))
                .map(client ->
                        ResponseEntity
                                .created(URI.create("/client/".concat(client.getId())))
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(mapper.toDtoBusiness(client)));
    }

    @Override
    public Mono<ResponseEntity<Void>> clientBusinessIdDelete(String id, ServerWebExchange exchange) {
        return clientService.findById(id).flatMap(client ->
                clientService.delete(client).then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)))
        ).defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
    }

    @Override
    public Mono<ResponseEntity<ClientBusinessDto>> clientBusinessIdGet(String id, ServerWebExchange exchange) {
        return clientService.findById(id).map(client -> ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(mapper.toDtoBusiness(client))
        ).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Override
    public Mono<ResponseEntity<Void>> clientPersonalIdDelete(String id, ServerWebExchange exchange) {
        return clientService.findById(id).flatMap(client ->
                clientService.delete(client).then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)))
        ).defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
    }

    @Override
    public Mono<ResponseEntity<ClientDto>> clientPersonalIdGet(String id, ServerWebExchange exchange) {
        return clientService.findById(id).map(client -> ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(mapper.toDto(client))
        ).defaultIfEmpty(ResponseEntity.notFound().build())
                .onErrorResume(WebClientResponseException.class,
                        ex -> ex.getRawStatusCode() == 404 ? Mono.empty() : Mono.error(ex));
    }

    @Override
    public Mono<ResponseEntity<Flux<ClientBusinessDto>>> findClientsBusiness(ServerWebExchange exchange) {
        return Mono.just(ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(clientService.findAll().map(mapper::toDtoBusiness)));
    }

    @Override
    public Mono<ResponseEntity<ClientBusinessDto>> updateClientBusiness(String id, Mono<NewClientBusinessDto> newClientBusinessDto, ServerWebExchange exchange) {
        return newClientBusinessDto.flatMap(newClient ->
                clientService.updateByIdentityNumber(mapper.toModelBusiness(newClient), id))
                .map(client -> ResponseEntity.created(
                        URI.create("/client/".concat(client.getId())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.toDtoBusiness(client)));
    }


    @Override
    public Mono<ResponseEntity<ClientDto>> addClient(Mono<NewClientDto> newClientDto, ServerWebExchange exchange) {
        return newClientDto.flatMap(clientDto -> clientService.save(mapper.toModel(clientDto)))
                .map(client ->
                        ResponseEntity
                                .created(URI.create("/client/".concat(client.getId())))
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(mapper.toDto(client))
                );
    }


    @Override
    public Mono<ResponseEntity<Flux<ClientDto>>> findClients(ServerWebExchange exchange) {
        return Mono.just(ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(clientService.findAll().map(mapper::toDto)));
    }

    @Override
    public Mono<ResponseEntity<ClientDto>> updateClient(String identityNumber,
                                                        Mono<NewClientDto> newClientDto, ServerWebExchange exchange) {
        return newClientDto.flatMap(newClient ->
                clientService.updateByIdentityNumber(mapper.toModel(newClient), identityNumber))
                .map(client -> ResponseEntity.created(
                        URI.create("/client/".concat(client.getId())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.toDto(client)));
    }
}
