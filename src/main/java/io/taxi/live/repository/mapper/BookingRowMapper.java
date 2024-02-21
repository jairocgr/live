package io.taxi.live.repository.mapper;

import io.taxi.live.domain.*;
import io.taxi.live.json.JsonSerializer;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@RequiredArgsConstructor
public class BookingRowMapper implements RowMapper<Booking> {

    private final JsonSerializer serializer;

    @Override
    public Booking mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Booking.builder()
            .id(rs.getObject("id", UUID.class))
            .status(getStatusFrom(rs))
            .origin(fromJson(rs.getString("origin"), Location.class))
            .destination(fromJson(rs.getString("destination"), Location.class))
            .client(fromJson(rs.getString("client"), Client.class))
            .taxi(getTaxi(rs))
            .createdAt(rs.getTimestamp("created_at").toInstant())
            .build();
    }

    private Taxi getTaxi(ResultSet rs) throws SQLException {
        var taxi = fromJson(rs.getString("taxi"), Taxi.class);
        if (taxi != null) {
            Driver driver = fromJson(rs.getString("driver"), Driver.class);
            taxi = taxi.toBuilder()
                .driver(driver)
                .build();
        }
        return taxi;
    }

    private BookingStatus getStatusFrom(ResultSet rs) throws SQLException {
        var status = rs.getString("status");
        return BookingStatus.valueOf(status);
    }

    public <T> T fromJson(final String json, Class<T> clazz) {
        return json == null ? null : serializer.fromJson(json, clazz);
    }
}
