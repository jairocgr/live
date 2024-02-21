package io.taxi.live.domain;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class NewTaxi {

    @NotNull
    private final UUID id;

    @NotNull
    @Size(min = 2)
    @Size(max = 32)
    private final String brand;

    @NotNull
    @Size(min = 2)
    @Size(max = 128)
    private final String model;

    @NotNull
    @Size(min = 2)
    @Size(max = 16)
    private final String registration;
}
