package com.bootcamp.msvcaccount.model.document;

import com.specification.api.dto.ClientAccountDto;
import com.specification.api.dto.ProductAccountDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@Document(collection = "account")
public class Account {

    @Id
    private String id;

    private List<ProductAccountDto> productAccountList;
    private ClientAccountDto clientAccount;

    public Account(List<ProductAccountDto> productAccountList, ClientAccountDto clientAccount) {
        this.productAccountList = productAccountList;
        this.clientAccount = clientAccount;
    }

    public Account(){}

}
