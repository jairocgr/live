package io.taxi.live.exception;

import java.util.UUID;

public class DriverNotFoundException extends RecordNotFoundException {

    public DriverNotFoundException(UUID id) {
        super("Driver \"%s\" not found".formatted(id));
    }
}
