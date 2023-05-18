package com.foundation.crud.mapper;

import com.foundation.crud.dto.CustomerRegistrationRequest;
import com.foundation.crud.model.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * Mapper interface for mapping between CustomerRegistrationRequest and Customer entities.
 */
@Mapper
public interface CustomerMapper {
    /**
     * Instance of the CustomerMapper interface.
     */
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    /**
     * Converts a CustomerRegistrationRequest object to a Customer object.
     *
     * @param registrationRequest the CustomerRegistrationRequest object to be converted.
     * @return the converted Customer object-
     */
    @Mapping(target = "id", ignore = true)
    Customer toCustomer(CustomerRegistrationRequest registrationRequest);

}