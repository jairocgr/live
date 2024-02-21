package io.taxi.live.repository;

import io.taxi.live.domain.Driver;
import io.taxi.live.exception.DriverNotFoundException;
import io.taxi.live.repository.mapper.DriverRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class DriverRepository {

    private static final String INSERT = """
        INSERT INTO live.driver (id, name)
        VALUES (uuid(:id), :name);
        """;

    private final NamedParameterJdbcTemplate jdbc;

    public void add(Driver driver) {
        jdbc.update(INSERT, toRecord(driver));
    }

    public Driver require(UUID id) {
        return get(id).orElseThrow(() -> new DriverNotFoundException(id));
    }

    public Optional<Driver> get(UUID id) {
        var query = "SELECT * FROM live.driver WHERE id = :id";
        var args = Map.of("id", id);
        return jdbc.query(query, args, new DriverRowMapper())
            .stream()
            .findFirst();
    }

    private HashMap<String, String> toRecord(Driver driver) {
        var parameters = new HashMap<String, String>();
        parameters.put("id", driver.getId().toString());
        parameters.put("name", driver.getName());
        return parameters;
    }

}
