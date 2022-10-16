package com.bootcamp.msvcclient.model.mapper;

import com.bootcamp.msvcclient.model.document.Client;
import com.specification.api.dto.ClientBusinessDto;
import com.specification.api.dto.ClientDto;
import com.specification.api.dto.NewClientBusinessDto;
import com.specification.api.dto.NewClientDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    ClientDto toDto(Client value);

    Client toModel(NewClientDto value);

    ClientBusinessDto toDtoBusiness(Client value);

    Client toModelBusiness(NewClientBusinessDto value);

}