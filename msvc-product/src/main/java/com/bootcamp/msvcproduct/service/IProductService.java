package com.bootcamp.msvcproduct.service;

import com.bootcamp.msvcproduct.model.document.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IProductService {
    Flux<Product> findAll();
//    Flux<Product> findAllByCategodyAndType(String category,String type);
    Mono<Product> findById(String id);
    Mono<Product> save(Product product);
    Mono<Void> delete (Product product);
    Mono<Product> update(Product product, String id);
}