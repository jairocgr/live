package io.taxi.live.repository.mapper;

import io.taxi.live.fixture.TaxiFixture;
import io.taxi.live.json.JsonSerializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class TaxiRowMapperTest {

    private TaxiRowMapper mapper;

    @BeforeEach
    void setUp() {
        var serializer = new JsonSerializer();
        mapper = new TaxiRowMapper(serializer);
    }

    @Test
    void shouldMapTaxi() throws SQLException {
        var mockResultSet = TaxiFixture.builder()
            .withFixedData()
            .buildResultSet();

        var expected = TaxiFixture.builder()
            .withFixedData()
            .build();

        var mapped = mapper.mapRow(mockResultSet, 0);

        assertThat(mapped)
            .usingRecursiveComparison()
            .isEqualTo(expected);
    }

    @Test
    void shouldMapOffDutyTaxi() throws SQLException {
        var mockResultSet = TaxiFixture.builder()
            .withFixedData()
            .withOffDutyStatus()
            .buildResultSet();

        var expected = TaxiFixture.builder()
            .withFixedData()
            .withOffDutyStatus()
            .build();

        var mapped = mapper.mapRow(mockResultSet, 0);

        assertThat(mapped)
            .usingRecursiveComparison()
            .isEqualTo(expected);
    }

}
