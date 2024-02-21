package io.taxi.live.service;

import io.taxi.live.domain.AcceptanceDetails;
import io.taxi.live.exception.BookingCantBeAcceptedException;
import io.taxi.live.exception.BookingCantBeFinishedException;
import io.taxi.live.exception.TaxiBusyException;
import io.taxi.live.repository.BookingRepository;
import io.taxi.live.repository.TaxiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static io.taxi.live.domain.BookingStatus.FINISHED;
import static io.taxi.live.domain.BookingStatus.ONGOING;
import static io.taxi.live.domain.TaxiStatus.BUSY;
import static io.taxi.live.domain.TaxiStatus.IDLE;
import static org.springframework.transaction.annotation.Isolation.SERIALIZABLE;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookings;
    private final TaxiRepository taxis;

    @Transactional(isolation = SERIALIZABLE)
    public void accept(UUID bookingId, AcceptanceDetails details) {
        var booking = bookings.require(bookingId);
        var driverId = details.driver();
        var taxi = taxis.requireByDriverId(driverId);

        if (booking.canNotBeAccepted()) {
            throw new BookingCantBeAcceptedException(booking);
        }

        if (taxi.isBusy()) {
            throw new TaxiBusyException();
        }

        bookings.setTaxi(booking, taxi);
        bookings.updateStatus(booking, ONGOING);
        taxis.updateStatus(taxi.getId(), BUSY);
    }

    @Transactional(isolation = SERIALIZABLE)
    public void finish(UUID bookingId) {
        var booking = bookings.require(bookingId);

        if (booking.canNotBeFinished()) {
            throw new BookingCantBeFinishedException(booking);
        }

        var taxiId = booking.getTaxi().getId();
        taxis.updateStatus(taxiId, IDLE);
        bookings.updateStatus(booking, FINISHED);
        bookings.removeTaxi(booking);
    }
}
