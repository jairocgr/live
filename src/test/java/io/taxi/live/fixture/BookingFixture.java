package io.taxi.live.fixture;


import io.taxi.live.domain.Booking;
import io.taxi.live.json.JsonSerializer;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

import static io.taxi.live.domain.BookingStatus.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BookingFixture implements Fixture<Booking> {

    private Booking booking;

    public static BookingFixture builder() {
        return new BookingFixture();
    }

    public BookingFixture withFixedData() {
        var taxi = TaxiFixture.builder()
            .withFixedData()
            .build();

        var origin = LocationFixture.builder()
            .withFixedData()
            .build();

        var destination = LocationFixture.builder()
            .withFixedData()
            .build();

        var client = ClientFixture.builder()
            .withFixedData()
            .build();

        booking = Booking.builder()
            .id(UUID.fromString("6c323d6b-fd15-42c1-9601-5b7d1fd818f9"))
            .status(ONGOING)
            .origin(origin)
            .destination(destination)
            .client(client)
            .taxi(taxi)
            .createdAt(Instant.parse("2024-02-03T10:15:30.00Z"))
            .build();

        return this;
    }

    public BookingFixture finishedBooking() {
        booking = booking.toBuilder()
            .status(FINISHED)
            .taxi(null)
            .build();
        return this;
    }

    public BookingFixture withOpenStatus() {
        booking = booking.toBuilder()
            .status(OPEN)
            .taxi(null)
            .build();
        return this;
    }

    public Booking build() {
        return booking;
    }

    public ResultSet buildResultSet() throws SQLException {
        var serializer = new JsonSerializer();
        var rs = mock(ResultSet.class);
        when(rs.getObject("id", UUID.class)).thenReturn(booking.getId());
        when(rs.getString("status")).thenReturn(booking.getStatus().toString());
        when(rs.getString("origin")).thenReturn(serializer.toJson(booking.getOrigin()));
        when(rs.getString("destination")).thenReturn(serializer.toJson(booking.getDestination()));
        when(rs.getString("client")).thenReturn(serializer.toJson(booking.getClient()));
        when(rs.getString("taxi")).thenReturn(serializer.toJson(booking.getTaxi()));

        if (booking.getTaxi() == null) {
            when(rs.getString("driver")).thenReturn(null);
        } else {
            when(rs.getString("driver")).thenReturn(serializer.toJson(booking.getTaxi().getDriver()));
        }

        var timestamp = Timestamp.from(booking.getCreatedAt());
        when(rs.getTimestamp("created_at")).thenReturn(timestamp);
        return rs;
    }
}
