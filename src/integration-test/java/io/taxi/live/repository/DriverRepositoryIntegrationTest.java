package io.taxi.live.repository;

import io.taxi.live.BaseIntegrationTest;
import io.taxi.live.exception.DriverNotFoundException;
import io.taxi.live.fixture.DriverFixture;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DriverRepositoryIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private DriverRepository repository;

    @Test
    void shouldAddAndRetrieveNewDriver() {
        var newDriver = DriverFixture.builder()
            .withFixedData()
            .build();

        var driverId = newDriver.getId();

        repository.add(newDriver);

        var retrieved = repository.require(driverId);

        assertThat(retrieved)
            .usingRecursiveComparison()
            .isEqualTo(newDriver);
    }

    @Test
    void shouldFailWhenRecordNotFound() {
        var unknownId = UUID.fromString("50680ac0-076f-4b21-9c54-575101d4f491");
        assertThrows(DriverNotFoundException.class, () -> repository.require(unknownId));
    }
}
