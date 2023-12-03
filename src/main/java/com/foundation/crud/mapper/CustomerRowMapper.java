package com.foundation.crud.mapper;

import com.foundation.crud.model.Customer;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * RowMapper implementation for mapping database rows to Customer objects.
 */
@Component
public class CustomerRowMapper implements RowMapper<Customer> {

    /**
     * Maps a row of the result set to a Customer object.
     *
     * @param rs the ResultSet to be mapped
     * @param rowNum the number of the current row
     * @return a Customer object representing the mapped row
     * @throws SQLException if a SQLException is encountered getting column values
     */
    @Override
    public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Customer(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getInt("age")
        );
    }
}
