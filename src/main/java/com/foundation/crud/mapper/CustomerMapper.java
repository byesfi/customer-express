package com.foundation.crud.mapper;

import com.foundation.crud.dto.CustomerRegistrationRequest;
import com.foundation.crud.dto.CustomerUpdateRequest;
import com.foundation.crud.model.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * Mapper interface for converting between CustomerRegistrationRequest,
 * CustomerUpdateRequest, and Customer entities.
 */
@Mapper
public interface CustomerMapper {

    /**
     * Singleton instance of the CustomerMapper interface.
     */
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    /**
     * Converts a CustomerRegistrationRequest object to a Customer entity.
     *
     * @param registrationRequest the CustomerRegistrationRequest object to be converted.
     * @return the converted Customer entity.
     */
    @Mapping(target = "id", ignore = true)
    Customer toCustomer(CustomerRegistrationRequest registrationRequest);

    /**
     * Converts a CustomerUpdateRequest object to a Customer entity.
     *
     * @param customerUpdateRequest the CustomerUpdateRequest object to be converted.
     * @return the converted Customer entity.
     */
    @Mapping(target = "id", ignore = true)
    Customer toCustomer(CustomerUpdateRequest customerUpdateRequest);

}