package com.bootcamp.msvcoperation.model.document;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Date;


@Builder
@Getter
@Setter
@AllArgsConstructor
@Document(collection = "operation")
public class Operation {

    @Id
    private String id;

    private String type;

    private String idAccount;

    private String idProductOne;

    private String idProductTwo;

    private Double balanceOperation;

    private Double balanceSpentOperation;

    private String dateOperation;


}





