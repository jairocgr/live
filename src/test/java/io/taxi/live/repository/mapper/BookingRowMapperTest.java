package io.taxi.live.repository.mapper;

import io.taxi.live.fixture.BookingFixture;
import io.taxi.live.json.JsonSerializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class BookingRowMapperTest {

    private BookingRowMapper mapper;

    @BeforeEach
    void setUp() {
        var serializer = new JsonSerializer();
        mapper = new BookingRowMapper(serializer);
    }

    @Test
    void shouldMapBooking() throws SQLException {
        var mockResultSet = BookingFixture.builder()
            .withFixedData()
            .buildResultSet();

        var expected = BookingFixture.builder()
            .withFixedData()
            .build();

        var mapped = mapper.mapRow(mockResultSet, 0);

        assertThat(mapped)
            .usingRecursiveComparison()
            .isEqualTo(expected);
    }

    @Test
    void shouldMapFinishedBooking() throws SQLException {
        var mockResultSet = BookingFixture.builder()
            .withFixedData()
            .finishedBooking()
            .buildResultSet();

        var expected = BookingFixture.builder()
            .withFixedData()
            .finishedBooking()
            .build();

        var mapped = mapper.mapRow(mockResultSet, 0);

        assertThat(mapped)
            .usingRecursiveComparison()
            .isEqualTo(expected);
    }

}
