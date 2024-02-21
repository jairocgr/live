package io.taxi.live.exception;

import java.util.UUID;

public class TaxiNotFoundException extends RecordNotFoundException {

    public TaxiNotFoundException(UUID id) {
        super("Taxi \"%s\" not found".formatted(id));
    }
}
