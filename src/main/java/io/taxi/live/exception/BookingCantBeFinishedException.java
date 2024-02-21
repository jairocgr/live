package io.taxi.live.exception;

import io.taxi.live.domain.Booking;
import io.taxi.live.domain.BookingStatus;
import lombok.Getter;

import java.util.UUID;

@Getter
public class BookingCantBeFinishedException extends BusinessException {

    private final UUID bookingId;
    private final BookingStatus status;

    public BookingCantBeFinishedException(Booking booking) {
        super("Booking can't be finished (status %s)".formatted(booking.getStatus()));
        bookingId = booking.getId();
        status = booking.getStatus();
    }
}
