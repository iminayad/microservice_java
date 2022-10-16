package com.bootcamp.msvcoperation.service;


import com.bootcamp.msvcoperation.model.document.Operation;
import com.specification.api.dto.NewOperationDto;
import com.specification.api.dto.OperationAccountDto;
import com.specification.api.dto.OperationDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface OperationService {

    Flux<Operation> findAll();

    Mono<Operation> findById(String id);

    Mono<Operation> save(Operation operation);

    Mono<Operation> saveOperation(Operation operation);

    Mono<Void> delete(Operation operation);

    Mono<Operation> update(Operation operation, String id);
}
