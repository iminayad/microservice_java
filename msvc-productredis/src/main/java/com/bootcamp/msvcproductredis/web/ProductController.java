package com.bootcamp.msvcproductredis.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bootcamp.msvcproductredis.service.IProductService;
import com.bootcamp.msvcproductredis.web.model.ProductModel;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/product")
@Slf4j
public class ProductController {
    @Autowired
    private final IProductService productService;

    @GetMapping()
    @Operation(summary = "Obtener lista de productos")
    public ResponseEntity<Object> getAll() throws Exception {
        List<ProductModel> response = productService.findAll();
        log.info("getAll" + "OK");
        log.debug(response.toString());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    
    @GetMapping(path = { "{id}" }, produces = { "application/json" })
    @Operation(summary = "Obtener productos por ID")
    public ResponseEntity<ProductModel> getById(@PathVariable("id") String id) throws Exception{
        ProductModel response = productService.findById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    
    @GetMapping(path = { "/codeProduct" }, produces = { "application/json" })
    @Operation(summary = "Obtener productos por codigo")
    public ResponseEntity<ProductModel> getByCodeProduct(@RequestParam String codeProduct) throws Exception{
        ProductModel response = productService.findProductByCodeProduct(codeProduct);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
   
    @GetMapping(path = { "/" }, produces = { "application/json" })
    @Operation(summary = "Obtener productos por descripción")
    public ResponseEntity<ProductModel> getByDescription(@RequestParam String description) throws Exception{
        ProductModel response = productService.findProductByDescription(description);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    
    @PostMapping()
    @Operation(summary = "Registrar productos")
    public ResponseEntity<Object> create(@RequestBody ProductModel productModel) throws Exception {
        ProductModel response = productService.create(productModel);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping(path = { "{id}" }, produces = { "application/json" })
    @Operation(summary = "Modificar productos")
    public void update(
            @PathVariable("id") String id,
            @RequestBody ProductModel productModel) throws Exception {
        productService.update(id, productModel);
    }

    @DeleteMapping({ "{id}" })
    @Operation(summary = "Eliminar productos por  ID")
    public void deleteById(@PathVariable("id") String id) throws Exception {
        productService.deleteById(id);
    }
}

