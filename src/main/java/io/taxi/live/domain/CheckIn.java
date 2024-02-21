package io.taxi.live.domain;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
@Builder(toBuilder = true)
public class CheckIn {

    @NotNull
    private final UUID driver;

    @NotNull
    @Min(-90) @Max(90)
    private final Double latitude;

    @NotNull
    @Min(-180) @Max(180)
    private final Double longitude;
}
