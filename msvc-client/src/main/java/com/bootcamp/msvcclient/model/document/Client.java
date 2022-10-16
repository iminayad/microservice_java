package com.bootcamp.msvcclient.model.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@Document(collection = "client")
public class Client implements Serializable {

    @Id
    private String id;

    private String name;
    private String identityType;
    private String identityNumber;
    private String segmentType;
    private String customerType;
    private String address;
    private String phoneNumber;

    private List<Object> holderList;
    private List<Object> signatoryList;

}
