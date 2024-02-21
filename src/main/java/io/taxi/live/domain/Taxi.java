package io.taxi.live.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

import static io.taxi.live.domain.TaxiStatus.BUSY;

@Getter
@Builder(toBuilder = true)
public class Taxi {

    private final UUID id;

    private final String brand;

    private final String model;

    private final String registration;

    private final TaxiStatus status;

    private final Double latitude;

    private final Double longitude;

    private final Driver driver;

    @JsonIgnore
    public boolean isActive() {
        return status.isActive();
    }

    @JsonIgnore
    public boolean isBusy() {
        return status == BUSY;
    }
}
