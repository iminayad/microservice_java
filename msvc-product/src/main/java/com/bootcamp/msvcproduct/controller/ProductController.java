package com.bootcamp.msvcproduct.controller;

import com.bootcamp.msvcproduct.model.mapper.ProductMapper;
import com.bootcamp.msvcproduct.service.IProductService;
import com.specification.api.ProductApi;
import com.specification.api.dto.ProductDto;
import com.specification.api.dto.NewProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
public class ProductController implements ProductApi {

    @Autowired
    private IProductService productService;

    @Autowired
    private ProductMapper mapper;


    @Override
    public Mono<ResponseEntity<ProductDto>> addProduct(Mono<NewProductDto> newProductDto, ServerWebExchange exchange) {
        return newProductDto.flatMap(ProductDto -> productService.save(mapper.toModel(ProductDto)))
                .map(product ->
                        ResponseEntity
                                .created(URI.create("/product/".concat(product.getId())))
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(mapper.toDto(product))
                );
    }

    @Override
    public Mono<ResponseEntity<Flux<ProductDto>>> findproduct(ServerWebExchange exchange) {
        return Mono.just(ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(productService.findAll().map(mapper::toDto)));
    }

    @Override
    public Mono<ResponseEntity<Flux<ProductDto>>> productCategoryTypeClientGet(String category, String type, ServerWebExchange exchange) {
        return null;
//        return Mono.just(ResponseEntity.ok()
//                .contentType(MediaType.APPLICATION_JSON)
//                .body(productService.findAllByCategodyAndType(category,type).map(mapper::toDto)));
    }

    @Override
    public Mono<ResponseEntity<Void>> productIdDelete(String id, ServerWebExchange exchange) {
        return productService.findById(id).flatMap(product ->
                productService.delete(product).then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)))
        ).defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
    }

    @Override
    public Mono<ResponseEntity<ProductDto>> productIdGet(String id, ServerWebExchange exchange) {
        return productService.findById(id)
                .map(product -> ResponseEntity
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.toDto(product)));
    }



    @Override
    public Mono<ResponseEntity<ProductDto>> updateProduct(String id, Mono<NewProductDto> newProductDto, ServerWebExchange exchange) {
        return newProductDto.flatMap(newProduct ->
                productService.update(mapper.toModel(newProduct), id))
                .map(product -> ResponseEntity.created(
                        URI.create("/product/".concat(product.getId())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.toDto(product)));
    }
}
