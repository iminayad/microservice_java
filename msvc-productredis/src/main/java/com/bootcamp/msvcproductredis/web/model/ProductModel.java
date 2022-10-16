package com.bootcamp.msvcproductredis.web.model;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductModel   implements Serializable {

    @JsonProperty("id")
    private String id;
    
    @JsonProperty("codeProduct")
    @NotBlank(message="El código del producto no puede estar vacio")
    private String codeProduct;

    @JsonProperty("type")
    @NotBlank(message="El tipo de producto no puede estar vacio")
    private String type;
   

    @JsonProperty("description")
    @NotBlank(message="La descripción no puede estar vacio")
    private String description;
}
