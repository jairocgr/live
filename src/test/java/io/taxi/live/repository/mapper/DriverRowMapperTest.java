package io.taxi.live.repository.mapper;

import io.taxi.live.fixture.DriverFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class DriverRowMapperTest {

    private DriverRowMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new DriverRowMapper();
    }

    @Test
    void shouldMapDriver() throws SQLException {
        var mockResultSet = DriverFixture.builder()
            .withFixedData()
            .buildResultSet();

        var expected = DriverFixture.builder()
            .withFixedData()
            .build();

        var mapped = mapper.mapRow(mockResultSet, 0);

        assertThat(mapped)
            .usingRecursiveComparison()
            .isEqualTo(expected);
    }

}
