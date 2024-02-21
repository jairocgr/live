package io.taxi.live.exception;

import java.util.UUID;

public class BookingNotFoundException extends RecordNotFoundException {

    public BookingNotFoundException(UUID id) {
        super("Booking \"%s\" not found".formatted(id));
    }
}
