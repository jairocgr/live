package io.taxi.live.service;

import io.taxi.live.BaseIntegrationTest;
import io.taxi.live.fixture.CheckInFixture;
import io.taxi.live.repository.TaxiRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static io.taxi.live.domain.TaxiStatus.IDLE;
import static io.taxi.live.domain.TaxiStatus.OFF;
import static org.assertj.core.api.Assertions.assertThat;

public class TaxiServiceIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private TaxiService service;

    @Autowired
    private TaxiRepository repository;

    @Test
    void testCheckInOffDutyTaxi() {
        // OFF duty taxi and drivers ready to be checked-in
        var taxiId = UUID.fromString("c54a7959-ff97-4bee-86c0-447bb424b3eb");
        var driverId = UUID.fromString("c54a7959-ff97-4bee-86c0-447bb424b3eb");

        var checkIn = CheckInFixture.builder()
            .withFixedData()
            .withDriver(driverId)
            .build();

        service.checkIn(taxiId, checkIn);

        var given = repository.require(taxiId);

        assertThat(given).satisfies(taxi -> {
            assertThat(taxi.getStatus()).isEqualTo(IDLE);
            assertThat(taxi.getLatitude()).isEqualTo(checkIn.getLatitude());
            assertThat(taxi.getLongitude()).isEqualTo(checkIn.getLongitude());
            assertThat(taxi.getDriver().getId()).isEqualTo(driverId);
        });
    }

    @Test
    void testCheckOutIdleTaxi() {
        // IDLE taxi driven by Rafael
        var taxiId = UUID.fromString("d6935b73-3e56-4c96-a7d0-82c60cca8066");

        service.checkOut(taxiId);

        var given = repository.require(taxiId);

        assertThat(given).satisfies(taxi -> {
            assertThat(taxi.getStatus()).isEqualTo(OFF);
            assertThat(taxi.getLatitude()).isNull();
            assertThat(taxi.getLongitude()).isNull();
            assertThat(taxi.getDriver()).isNull();
        });
    }
}
