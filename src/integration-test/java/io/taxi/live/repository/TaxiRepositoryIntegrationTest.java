package io.taxi.live.repository;

import io.taxi.live.BaseIntegrationTest;
import io.taxi.live.domain.Taxi;
import io.taxi.live.exception.DriverDontHaveTaxiFoundException;
import io.taxi.live.exception.TaxiNotFoundException;
import io.taxi.live.fixture.NewTaxiFixture;
import io.taxi.live.fixture.TaxiFixture;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static io.taxi.live.domain.TaxiStatus.IDLE;
import static io.taxi.live.domain.TaxiStatus.OFF;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TaxiRepositoryIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private TaxiRepository repository;

    @Test
    void shouldAddAndRetrieveNewTaxi() {
        var newTaxi = NewTaxiFixture.builder()
            .withFixedData()
            .build();

        var expected = TaxiFixture.builder()
            .withFixedData()
            .withOffDutyStatus()
            .build();

        repository.add(newTaxi);

        var given = repository.require(newTaxi.getId());

        assertThat(given)
            .usingRecursiveComparison()
            .isEqualTo(expected);
    }

    @Test
    void shouldListCabsByStatus() {
        var offDutyCabs = repository.all(OFF);

        assertThat(offDutyCabs)
            .hasSize(4)
            .hasOnlyElementsOfType(Taxi.class)
            .allSatisfy(taxi -> {
                assertThat(taxi.getStatus()).isEqualTo(OFF);
            });
    }

    @Test
    void shouldFindTaxiByDriver() {
        var driverId = UUID.fromString("a5bbc872-93b3-4645-800f-289a40b52672");
        var expectedTaxiId = UUID.fromString("3c3a2571-1a4f-4914-9740-18ca3d88c359");

        var given = repository.requireByDriverId(driverId);

        assertThat(given).satisfies(taxi -> {
            assertThat(taxi.getId()).isEqualTo(expectedTaxiId);
            assertThat(taxi.getStatus()).isEqualTo(IDLE);
            assertThat(taxi.getDriver().getId()).isEqualTo(driverId);
        });
    }

    @Test
    void shouldFailWhenRecordNotFound() {
        var unknownId = UUID.fromString("50680ac0-076f-4b21-9c54-575101d4f491");
        // Amelia driver
        var offDutyDriver = UUID.fromString("fdc336b9-568a-4a0b-806d-cbbb720f6a44");
        assertThrows(TaxiNotFoundException.class, () -> repository.require(unknownId));
        assertThrows(DriverDontHaveTaxiFoundException.class, () -> repository.requireByDriverId(offDutyDriver));
    }
}
