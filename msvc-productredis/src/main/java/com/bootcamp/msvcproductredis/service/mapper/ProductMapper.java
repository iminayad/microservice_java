package com.bootcamp.msvcproductredis.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.bootcamp.msvcproductredis.domain.Product;
import com.bootcamp.msvcproductredis.web.model.ProductModel;


@Mapper(componentModel = "spring")
public interface ProductMapper {
    
    Product productModelToProduct (ProductModel productModel);

    ProductModel productToProductModel (Product product);

    List<ProductModel> productToProductModels(List<Product> product);


    @Mapping(target = "id", ignore = true)
    void update(@MappingTarget Product entity, ProductModel updateEntity);
}
