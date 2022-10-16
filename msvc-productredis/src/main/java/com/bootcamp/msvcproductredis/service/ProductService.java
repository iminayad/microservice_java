package com.bootcamp.msvcproductredis.service;

import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.bootcamp.msvcproductredis.domain.Product;
import com.bootcamp.msvcproductredis.repository.ProductRepository;
import com.bootcamp.msvcproductredis.service.mapper.ProductMapper;
import com.bootcamp.msvcproductredis.web.model.ProductModel;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public List<ProductModel> findAll() throws Exception {
        List<Product> products = productRepository.findAll();
        return productMapper.productToProductModels(products);
    }
    @Cacheable(value = "productCache")
    @Override
    public ProductModel findById(String id) throws Exception {
        Optional<Product> product = productRepository.findById(id);
        if(product.isPresent())	return productMapper.productToProductModel(product.get());
        else throw new Exception("No se encontraron datos");
    }

    @Override
    public ProductModel findProductByDescription(String description) throws Exception {
        Optional<Product> product = productRepository.findProductByDescription(description);
        if(product.isPresent())	return productMapper.productToProductModel(product.get());
        else throw new Exception("No se encontraron datos");
    }

    @Override
    public ProductModel create(ProductModel productModel) throws Exception {
    	
    	List<Product> productList = productRepository.findProductByCodeProduct(productModel.getCodeProduct());
    	if(productList.size() != 0) {
  	    	throw new Exception("El codigo del producto ya se encuentra registrado");    	
  	    }
    	else {
        Product product = productRepository.save(productMapper.productModelToProduct(productModel));
        return productMapper.productToProductModel(product);
    	}
    }

    @Override
    public void update(String id, ProductModel productModel) throws Exception {
        Optional<Product> productOptional = productRepository.findById(id);

        if(productOptional.isPresent()) {
            Product productToUpdate = productOptional.get();
            productMapper.update(productToUpdate, productModel);
            productRepository.save(productToUpdate);
        }
        else throw new Exception("No se encontraron datos");
    }

    @Override
    public void deleteById(String id) throws Exception {
        productRepository.deleteById(id);
    }

	@Override
	public ProductModel findProductByCodeProduct(String codeProduct) throws Exception {
		  List<Product> product = productRepository.findProductByCodeProduct(codeProduct);
		  if(product.size() != 0)	return productMapper.productToProductModel(product.get(0));
	        else throw new Exception("No se encontraron datos");
	}


}