package io.taxi.live.repository.mapper;

import io.taxi.live.domain.Driver;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class DriverRowMapper implements RowMapper<Driver> {
    @Override
    public Driver mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Driver.builder()
            .id(rs.getObject("id", UUID.class))
            .name(rs.getString("name"))
            .build();
    }
}
