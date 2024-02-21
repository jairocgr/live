package io.taxi.live.exception;

import java.util.UUID;

public class DriverDontHaveTaxiFoundException extends RecordNotFoundException {

    public DriverDontHaveTaxiFoundException(UUID driverId) {
        super("Driver \"%s\" don't have any taxi checked".formatted(driverId));
    }
}
