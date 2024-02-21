package io.taxi.live.service;

import io.taxi.live.exception.TaxiBusyException;
import io.taxi.live.exception.TaxiCantBeCheckedInException;
import io.taxi.live.fixture.CheckInFixture;
import io.taxi.live.fixture.DriverFixture;
import io.taxi.live.fixture.TaxiFixture;
import io.taxi.live.repository.DriverRepository;
import io.taxi.live.repository.TaxiRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.taxi.live.domain.TaxiStatus.IDLE;
import static io.taxi.live.domain.TaxiStatus.OFF;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TaxiServiceTest {

    private TaxiService service;

    private TaxiRepository taxis;
    private DriverRepository drivers;

    @BeforeEach
    void setUp() {
        taxis = mock(TaxiRepository.class);
        drivers = mock(DriverRepository.class);
        service = new TaxiService(taxis, drivers);
    }

    @Test
    void shouldCheckInAOffDutyTaxi() {
        var checkIn = CheckInFixture.builder()
            .withFixedData()
            .build();

        var taxi = TaxiFixture.builder()
            .withFixedData()
            .withOffDutyStatus()
            .build();

        var taxiId = taxi.getId();
        var driverId = checkIn.getDriver();

        var driver = DriverFixture.builder()
            .withFixedData()
            .build();

        when(taxis.require(taxiId)).thenReturn(taxi);
        when(drivers.require(driverId)).thenReturn(driver);

        service.checkIn(taxiId, checkIn);

        verify(taxis).setDriver(taxiId, driver);
        verify(taxis).setLocation(taxiId, checkIn.getLatitude(), checkIn.getLongitude());
        verify(taxis).updateStatus(taxiId, IDLE);
    }

    @Test
    void mustNotCheckInAnActiveTaxi() {
        var checkIn = CheckInFixture.builder()
            .withFixedData()
            .build();

        var taxi = TaxiFixture.builder()
            .withFixedData()
            .build();

        var taxiId = taxi.getId();

        when(taxis.require(taxiId)).thenReturn(taxi);

        assertThrows(TaxiCantBeCheckedInException.class, () -> {
            service.checkIn(taxiId, checkIn);
        });

        verify(taxis, never()).updateStatus(any(), any());
    }

    @Test
    void mustCheckOutAnIdleTaxi() {
        var taxi = TaxiFixture.builder()
            .withFixedData()
            .build();

        var taxiId = taxi.getId();

        when(taxis.require(taxiId)).thenReturn(taxi);

        service.checkOut(taxiId);

        verify(taxis).rmDriverFrom(taxi);
        verify(taxis).resetLocation(taxiId);
        verify(taxis).updateStatus(taxiId, OFF);
    }

    @Test
    void mustNotCheckoutBusyTaxi() {
        var taxi = TaxiFixture.builder()
            .withFixedData()
            .withBusyStatus()
            .build();

        var taxiId = taxi.getId();

        when(taxis.require(taxiId)).thenReturn(taxi);

        assertThrows(TaxiBusyException.class, () -> {
            service.checkOut(taxiId);
        });

        verify(taxis, never()).rmDriverFrom(any());
        verify(taxis, never()).resetLocation(any());
        verify(taxis, never()).updateStatus(any(), any());
    }
}
