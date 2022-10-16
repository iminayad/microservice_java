package com.bootcamp.msvcoperation.model.mapper;

import com.bootcamp.msvcoperation.model.document.Operation;

import com.specification.api.dto.*;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OperationMapper {

    OperationDto toDto(Operation value);

    Operation toModel(NewOperationDto value);

    NewAccountDto toAccountDto(AccountDto value);

    OperationDto toAccountDto(Operation value);
}