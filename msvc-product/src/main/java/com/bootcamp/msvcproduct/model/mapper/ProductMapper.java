package com.bootcamp.msvcproduct.model.mapper;

import com.bootcamp.msvcproduct.model.document.Product;
import com.specification.api.dto.NewProductDto;
import com.specification.api.dto.ProductDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDto toDto(Product product);

    Product toModel(NewProductDto productModel);

    List<ProductDto> listToModels(List<Product> products);

}