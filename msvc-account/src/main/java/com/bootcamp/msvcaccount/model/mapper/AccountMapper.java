package com.bootcamp.msvcaccount.model.mapper;

import com.bootcamp.msvcaccount.model.document.Account;
import com.specification.api.dto.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    ProductAccountDto toProductAccount(ProductDto value);
    ProductDto toProductDto(ProductAccountDto value);

    ClientAccountDto toClientAccount(ClientDto value);

    @Mapping(source = "productAccountList", target = "productList")
    @Mapping(source = "clientAccount", target = "client")
    AccountDto toAccountDto(Account value);

}
