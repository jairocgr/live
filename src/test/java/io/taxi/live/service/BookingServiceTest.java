package io.taxi.live.service;

import io.taxi.live.domain.AcceptanceDetails;
import io.taxi.live.exception.BookingCantBeAcceptedException;
import io.taxi.live.exception.BookingCantBeFinishedException;
import io.taxi.live.exception.TaxiBusyException;
import io.taxi.live.fixture.BookingFixture;
import io.taxi.live.fixture.TaxiFixture;
import io.taxi.live.repository.BookingRepository;
import io.taxi.live.repository.TaxiRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.taxi.live.domain.BookingStatus.FINISHED;
import static io.taxi.live.domain.BookingStatus.ONGOING;
import static io.taxi.live.domain.TaxiStatus.BUSY;
import static io.taxi.live.domain.TaxiStatus.IDLE;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class BookingServiceTest {

    private BookingService service;
    private BookingRepository bookings;
    private TaxiRepository taxis;

    @BeforeEach
    void setUo() {
        bookings = mock(BookingRepository.class);
        taxis = mock(TaxiRepository.class);
        service = new BookingService(bookings, taxis);
    }

    @Test
    void driverShouldBeAbleToAcceptAOpenBooking() {
        var booking = BookingFixture.builder()
            .withFixedData()
            .withOpenStatus()
            .build();

        var taxi = TaxiFixture.builder()
            .withFixedData()
            .build();

        var driver = taxi.getDriver();
        var driverId = driver.getId();
        var bookingId = booking.getId();
        var taxiId = taxi.getId();

        var details = new AcceptanceDetails(driverId);

        when(bookings.require(bookingId)).thenReturn(booking);
        when(taxis.requireByDriverId(driverId)).thenReturn(taxi);

        service.accept(bookingId, details);

        verify(bookings).setTaxi(booking, taxi);
        verify(bookings).updateStatus(booking, ONGOING);
        verify(taxis).updateStatus(taxiId, BUSY);
    }

    @Test
    void cantAcceptAOngoingBooking() {
        var booking = BookingFixture.builder()
            .withFixedData()
            .build();

        var taxi = TaxiFixture.builder()
            .withFixedData()
            .build();

        var driver = taxi.getDriver();
        var driverId = driver.getId();
        var bookingId = booking.getId();

        var details = new AcceptanceDetails(driverId);

        when(bookings.require(bookingId)).thenReturn(booking);
        when(taxis.requireByDriverId(driverId)).thenReturn(taxi);

        assertThrows(BookingCantBeAcceptedException.class, () -> {
            service.accept(bookingId, details);
        });

        verify(bookings, never()).setTaxi(any(), any());
        verify(bookings, never()).updateStatus(any(), any());
        verify(taxis, never()).updateStatus(any(), any());
    }

    @Test
    void cantAcceptIfDriverHasAnOngoingBooking() {
        var booking = BookingFixture.builder()
            .withFixedData()
            .withOpenStatus()
            .build();

        var taxi = TaxiFixture.builder()
            .withFixedData()
            .withBusyStatus()
            .build();

        var driver = taxi.getDriver();
        var driverId = driver.getId();
        var bookingId = booking.getId();

        var details = new AcceptanceDetails(driverId);

        when(bookings.require(bookingId)).thenReturn(booking);
        when(taxis.requireByDriverId(driverId)).thenReturn(taxi);

        assertThrows(TaxiBusyException.class, () -> {
            service.accept(bookingId, details);
        });

        verify(bookings, never()).setTaxi(any(), any());
        verify(bookings, never()).updateStatus(any(), any());
        verify(taxis, never()).updateStatus(any(), any());
    }

    @Test
    void shouldBeAbleToFinishAnOngoingBooking() {
        var booking = BookingFixture.builder()
            .withFixedData()
            .build();

        var bookingId = booking.getId();
        var taxiId = booking.getTaxi().getId();

        when(bookings.require(bookingId)).thenReturn(booking);

        service.finish(bookingId);

        verify(taxis).updateStatus(taxiId, IDLE);
        verify(bookings).removeTaxi(booking);
        verify(bookings).updateStatus(booking, FINISHED);
    }

    @Test
    void shouldNotFinishAnOpenBooking() {
        var booking = BookingFixture.builder()
            .withFixedData()
            .withOpenStatus()
            .build();

        var bookingId = booking.getId();

        when(bookings.require(bookingId)).thenReturn(booking);

        assertThrows(BookingCantBeFinishedException.class, () -> {
            service.finish(bookingId);
        });

        verify(taxis, never()).updateStatus(any(), any());
        verify(bookings, never()).removeTaxi(any());
        verify(bookings, never()).updateStatus(any(), any());
    }

}
