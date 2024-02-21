package io.taxi.live.domain;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Location {
    @NotBlank
    private final String address;

    @NotBlank
    private final String city;

    @NotBlank
    private final String state;

    @NotBlank
    private final String country;

    @NotNull
    @Min(-90) @Max(90)
    private final Double latitude;

    @NotNull
    @Min(-180) @Max(180)
    private final Double longitude;
}
