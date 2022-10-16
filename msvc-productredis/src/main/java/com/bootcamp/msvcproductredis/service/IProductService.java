package com.bootcamp.msvcproductredis.service;

import java.util.List;

import com.bootcamp.msvcproductredis.web.model.ProductModel;


public interface IProductService {
	
	List<ProductModel> findAll() throws Exception;

    ProductModel findById(String id) throws Exception;

    ProductModel findProductByDescription(String description) throws Exception;
    
    ProductModel findProductByCodeProduct(String codeProduct) throws Exception;

    ProductModel create(ProductModel productModel) throws Exception;

    void update(String id, ProductModel productModel) throws Exception;

    void deleteById(String id) throws Exception;
}
