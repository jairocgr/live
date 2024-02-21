package io.taxi.live.exception;

import io.taxi.live.domain.Taxi;
import io.taxi.live.domain.TaxiStatus;
import lombok.Getter;

@Getter
public class TaxiCantBeCheckedInException extends BusinessException {

    private final TaxiStatus currentStatus;

    public TaxiCantBeCheckedInException(Taxi taxi) {
        super("Taxi \"%s\" can't be checked-in".formatted(taxi.getId()));
        currentStatus = taxi.getStatus();
    }

}
