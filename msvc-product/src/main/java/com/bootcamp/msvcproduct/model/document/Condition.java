package com.bootcamp.msvcproduct.model.document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "condition")
public class Condition {

    @Id
    private String id;

    private boolean hasMaintenanceFee;
    private Double maintenanceFee;

    private boolean hasMonthlyTransactionLimit;
    private Integer monthlyTransactionLimit;

    private boolean hasDailyMonthlyTransactionLimit;
    private Integer dailyMonthlyTransactionLimit;

    private Integer productPerPersonLimit;
    private Integer productPerBusinessLimit;


}

