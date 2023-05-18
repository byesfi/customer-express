package com.foundation.crud.mapper;

import com.foundation.crud.dto.CustomerRegistrationRequest;
import com.foundation.crud.model.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    @Mapping(target = "id", ignore = true)
    Customer toCustomer(CustomerRegistrationRequest registrationRequest);

}