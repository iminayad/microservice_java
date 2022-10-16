package com.bootcamp.msvcoperation.controller;



import com.bootcamp.msvcoperation.model.mapper.OperationMapper;
import com.bootcamp.msvcoperation.service.OperationService;

import com.specification.api.OperationApi;
import com.specification.api.dto.NewClientDto;
import com.specification.api.dto.NewOperationDto;
import com.specification.api.dto.OperationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
public class OperationController implements OperationApi {



    @Autowired
    private OperationService operationService;

    @Autowired
    private OperationMapper mapper;

    @Override
    public Mono<ResponseEntity<OperationDto>> addOperation(Mono<NewOperationDto> newOperationDto, ServerWebExchange exchange) {
        return newOperationDto.flatMap(operationDto -> operationService.saveOperation(mapper.toModel(operationDto)))
                .map(operation ->
                        ResponseEntity
                                .created(URI.create("/operation/".concat(operation.getId())))
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(mapper.toDto(operation))
                );

    }

    @Override
    public Mono<ResponseEntity<Flux<OperationDto>>> findOperations(ServerWebExchange exchange) {
        return Mono.just(ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(operationService.findAll().map(mapper::toDto)));
    }

    @Override
    public Mono<ResponseEntity<Void>> operationIdDelete(String id, ServerWebExchange exchange) {

        return operationService.findById(id).flatMap(operation ->
                operationService.delete(operation).then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)))
        ).defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
    }

    @Override
    public Mono<ResponseEntity<OperationDto>> operationIdGet(String id, ServerWebExchange exchange) {

        return operationService.findById(id).map(operation -> ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(mapper.toDto(operation))
        ).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Override
    public Mono<ResponseEntity<OperationDto>> updateAccount(String id, Mono<NewOperationDto> newOperationDto, ServerWebExchange exchange) {


        return newOperationDto.flatMap(newProduct ->
                operationService.update(mapper.toModel(newProduct), id))
                .map(product -> ResponseEntity.created(
                        URI.create("/operation/".concat(product.getId())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.toDto(product)));

    }
}
