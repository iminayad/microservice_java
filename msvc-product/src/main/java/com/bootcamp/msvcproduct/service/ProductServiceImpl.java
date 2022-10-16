package com.bootcamp.msvcproduct.service;

import com.bootcamp.msvcproduct.model.document.Product;
import com.bootcamp.msvcproduct.repository.IProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Implementacion de Metodos del Service Product.
 */
@Slf4j
@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private IProductRepository productRepository;

    @Override
    public Flux<Product> findAll() {
        log.info("ProductServiceImpl: findAll");
        return productRepository.findAll();
    }
//    @Override
//    public Flux<Product> findAllByCategodyAndType(String category,String type) {
//        log.info("ProductServiceImpl: findAllByCategodyAndType");
//        return productRepository.findAllByCategodyAndType(category,type);
//    }
    @Override
    public Mono<Product> findById(String id) {
        log.info("ProductServiceImpl: findById");
        return productRepository.findById(id);
    }

    @Override
    public Mono<Product> save(Product product) {
        log.info("ProductServiceImpl: save");
        return productRepository.save(product);
    }

    @Override
    public Mono<Void> delete(Product product) {
        log.info("ProductServiceImpl: delete");
        return productRepository.delete(product);
    }
    @Override
    public Mono<Product> update(Product product, String id) {

        return productRepository.findById(id)
                .flatMap(productDB -> {
                    return productRepository.save(productDB);
                });

    }
}