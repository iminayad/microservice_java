package com.bootcamp.msvcproductredis.repository;


import org.springframework.data.mongodb.repository.MongoRepository;


import com.bootcamp.msvcproductredis.domain.Product;


import java.util.List;
import java.util.Optional;


public interface ProductRepository extends MongoRepository<Product, String> {

     Optional<Product> findProductByDescription(String description);
     	List<Product>findProductByCodeProduct(String codeProduct);
}
