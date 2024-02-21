package io.taxi.live.model;

import io.taxi.live.fixture.TaxiFixture;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TaxiTest {

    @Test
    void iddleTaxiIsActiveTaxi() {
        var taxi = TaxiFixture.builder()
            .withFixedData()
            .build();

        assertTrue(taxi.isActive());
    }

    @Test
    void busyTaxiIsAlsoActive() {
        var taxi = TaxiFixture.builder()
            .withFixedData()
            .withBusyStatus()
            .build();

        assertTrue(taxi.isActive());
    }

    @Test
    void offTaxiIsInactive() {
        var taxi = TaxiFixture.builder()
            .withFixedData()
            .withOffDutyStatus()
            .build();

        assertFalse(taxi.isActive());
    }

    @Test
    void checkBusyStatus() {
        var taxi = TaxiFixture.builder()
            .withFixedData()
            .withBusyStatus()
            .build();

        assertTrue(taxi.isBusy());
    }
}
