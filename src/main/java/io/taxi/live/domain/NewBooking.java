package io.taxi.live.domain;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class NewBooking {

    @NotNull
    private final UUID id;

    @NotNull
    private final Location origin;

    @NotNull
    private final Location destination;

    @NotNull
    private final Client client;
}
