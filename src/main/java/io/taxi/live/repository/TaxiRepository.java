package io.taxi.live.repository;

import io.taxi.live.domain.Driver;
import io.taxi.live.domain.NewTaxi;
import io.taxi.live.domain.Taxi;
import io.taxi.live.domain.TaxiStatus;
import io.taxi.live.exception.DriverDontHaveTaxiFoundException;
import io.taxi.live.exception.TaxiNotFoundException;
import io.taxi.live.json.JsonSerializer;
import io.taxi.live.repository.mapper.TaxiRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@RequiredArgsConstructor
public class TaxiRepository {

    private static final String INSERT = """
        INSERT INTO live.taxi (id, brand, model, registration)
        VALUES (uuid(:id), :brand, :model, :registration);
        """;

    private final NamedParameterJdbcTemplate jdbc;
    private final JsonSerializer serializer;

    public void add(NewTaxi taxi) {
        jdbc.update(INSERT, toRecord(taxi));
    }

    public List<Taxi> all(TaxiStatus status) {
        var query = """
            SELECT t.*, to_jsonb(d) as driver
            FROM live.taxi t
            LEFT JOIN live.driver d ON (d.id = t.driver_id)
            WHERE status = '%s'
            """.formatted(status);
        return jdbc.query(query, new TaxiRowMapper(serializer));
    }

    public Taxi require(UUID id) {
        return get(id).orElseThrow(() -> new TaxiNotFoundException(id));
    }

    private Optional<Taxi> get(UUID id) {
        var query = """
            SELECT t.*, to_jsonb(d) as driver
            FROM live.taxi t
            LEFT JOIN live.driver d ON (d.id = t.driver_id)
            WHERE t.id = :id
            """;
        var args = Map.of("id", id);
        return jdbc.query(query, args, new TaxiRowMapper(serializer))
            .stream()
            .findFirst();
    }

    public Taxi requireByDriverId(UUID driverId) {
        return getDrivenBy(driverId).orElseThrow(() -> new DriverDontHaveTaxiFoundException(driverId));
    }

    private Optional<Taxi> getDrivenBy(UUID driverId) {
        var query = """
            SELECT t.*, to_jsonb(d) as driver
            FROM live.taxi t
            LEFT JOIN live.driver d ON (d.id = t.driver_id)
            WHERE t.driver_id = :driver_id
            """;
        var args = Map.of("driver_id", driverId);
        return jdbc.query(query, args, new TaxiRowMapper(serializer))
            .stream()
            .findFirst();
    }

    public void setDriver(UUID taxiId, Driver driver) {
        var cmd = "UPDATE live.taxi SET driver_id = :driver_id WHERE id = :taxi_id";
        var args = Map.of(
            "driver_id", driver.getId(),
            "taxi_id", taxiId
        );
        jdbc.update(cmd, args);
    }

    public void rmDriverFrom(Taxi taxi) {
        var cmd = "UPDATE live.taxi SET driver_id = null WHERE id = :taxi_id";
        var args = Map.of("taxi_id", taxi.getId());
        jdbc.update(cmd, args);
    }

    public void updateStatus(UUID taxiId, TaxiStatus status) {
        var cmd = "UPDATE live.taxi SET status = '%s' WHERE id = :id".formatted(status);
        var args = Map.of("id", taxiId);
        jdbc.update(cmd, args);
    }

    public void setLocation(UUID taxiId, Double latitude, Double longitude) {
        var cmd = "UPDATE live.taxi SET latitude=:latitude, longitude=:longitude WHERE id = :id";
        var args = Map.of(
            "latitude", latitude,
            "longitude", longitude,
            "id", taxiId
        );
        jdbc.update(cmd, args);
    }

    public void resetLocation(UUID taxiId) {
        var cmd = "UPDATE live.taxi SET latitude=NULL, longitude=NULL WHERE id = :id";
        var args = Map.of("id", taxiId);
        jdbc.update(cmd, args);
    }

    private HashMap<String, String> toRecord(NewTaxi taxi) {
        var parameters = new HashMap<String, String>();
        parameters.put("id", taxi.getId().toString());
        parameters.put("brand", taxi.getBrand());
        parameters.put("model", taxi.getModel());
        parameters.put("registration", taxi.getRegistration());
        return parameters;
    }

}
