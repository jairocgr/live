package io.taxi.live.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

import static io.taxi.live.domain.BookingStatus.ONGOING;
import static io.taxi.live.domain.BookingStatus.OPEN;

@Getter
@Builder(toBuilder = true)
public class Booking {

    private final UUID id;

    private final BookingStatus status;

    private final Location origin;

    private final Location destination;

    private final Client client;

    private final Taxi taxi;

    private final Instant createdAt;

    public boolean canBeAccepted() {
        return status == OPEN;
    }

    public boolean canNotBeAccepted() {
        return !canBeAccepted();
    }

    public boolean canNotBeFinished() {
        return !canBeFinished();
    }

    public boolean canBeFinished() {
        return status == ONGOING;
    }
}
