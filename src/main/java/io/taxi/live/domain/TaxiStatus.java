package io.taxi.live.domain;

import lombok.Getter;

@Getter
public enum TaxiStatus {
    BUSY(true), IDLE(true), OFF(false);

    private final boolean active;

    TaxiStatus(boolean active) {
        this.active = active;
    }

}
