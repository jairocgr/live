package io.taxi.live.repository;

import io.taxi.live.domain.Booking;
import io.taxi.live.domain.BookingStatus;
import io.taxi.live.domain.NewBooking;
import io.taxi.live.domain.Taxi;
import io.taxi.live.exception.BookingNotFoundException;
import io.taxi.live.json.JsonSerializer;
import io.taxi.live.repository.mapper.BookingRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@RequiredArgsConstructor
public class BookingRepository {

    private static final String INSERT = """
        INSERT INTO live.booking (id, origin, destination, client)
        VALUES (uuid(:id), :origin::jsonb, :destination::jsonb, :client::jsonb);
        """;

    private final NamedParameterJdbcTemplate jdbc;
    private final JsonSerializer serializer;

    public List<Booking> all(BookingStatus status) {
        var query = """
        SELECT b.*,
            to_jsonb(t) as taxi,
            to_jsonb(d) as driver
        FROM live.booking b
        LEFT JOIN live.taxi t ON (b.taxi_id = t.id)
        LEFT JOIN live.driver d ON (t.driver_id = d.id)
        WHERE b.status = '%s'""".formatted(status);
        return jdbc.query(query, new BookingRowMapper(serializer));
    }

    public void setTaxi(Booking booking, Taxi taxi) {
        var cmd = "UPDATE live.booking SET taxi_id = :taxi_id WHERE id = :id";
        var args = Map.of(
            "taxi_id", taxi.getId(),
            "id", booking.getId()
        );
        jdbc.update(cmd, args);
    }

    public void removeTaxi(Booking booking) {
        var cmd = "UPDATE live.booking SET taxi_id = null WHERE id = :id";
        var args = Map.of("id", booking.getId());
        jdbc.update(cmd, args);
    }

    public void updateStatus(Booking booking, BookingStatus status) {
        var cmd = """
            UPDATE live.booking SET status = '%s'
            WHERE id = :id""".formatted(status);
        var args = Map.of("id", booking.getId());
        jdbc.update(cmd, args);
    }

    public Booking require(UUID id) {
        return get(id).orElseThrow(() -> new BookingNotFoundException(id));
    }

    private Optional<Booking> get(UUID id) {
        var query = """
        SELECT b.*,
            to_jsonb(t) as taxi,
            to_jsonb(d) as driver
        FROM live.booking b
        LEFT JOIN live.taxi t ON (b.taxi_id = t.id)
        LEFT JOIN live.driver d ON (t.driver_id = d.id)
        WHERE b.id = :id""";
        var args = Map.of("id", id);
        return jdbc.query(query, args, new BookingRowMapper(serializer))
            .stream()
            .findFirst();
    }

    public void add(NewBooking booking) {
        jdbc.update(INSERT, toRecord(booking));
    }

    private HashMap<String, String> toRecord(NewBooking booking) {
        var parameters = new HashMap<String, String>();
        parameters.put("id", booking.getId().toString());
        parameters.put("origin", serializer.toJson(booking.getOrigin()));
        parameters.put("destination", serializer.toJson(booking.getDestination()));
        parameters.put("client", serializer.toJson(booking.getClient()));
        return parameters;
    }

}
