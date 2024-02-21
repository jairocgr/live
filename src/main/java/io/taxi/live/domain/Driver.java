package io.taxi.live.domain;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class Driver {

    @NotNull
    private final UUID id;

    @NotNull
    @Size(min=2, max = 128)
    private final String name;
}
