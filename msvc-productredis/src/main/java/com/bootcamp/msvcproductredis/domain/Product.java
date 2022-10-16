package com.bootcamp.msvcproductredis.domain;

import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

import io.swagger.v3.oas.annotations.servers.Server;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
@Document(collection = "Product")
public class Product implements Serializable {
	
	
    @Id
    private String id;
    
    private String codeProduct;

    private String type;

    private String description;

}
