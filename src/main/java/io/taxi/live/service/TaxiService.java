package io.taxi.live.service;

import io.taxi.live.domain.CheckIn;
import io.taxi.live.exception.TaxiBusyException;
import io.taxi.live.exception.TaxiCantBeCheckedInException;
import io.taxi.live.repository.DriverRepository;
import io.taxi.live.repository.TaxiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static io.taxi.live.domain.TaxiStatus.IDLE;
import static io.taxi.live.domain.TaxiStatus.OFF;
import static org.springframework.transaction.annotation.Isolation.SERIALIZABLE;

@Service
@RequiredArgsConstructor
public class TaxiService {

    private final TaxiRepository taxis;
    private final DriverRepository drivers;

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void checkIn(UUID taxiId, CheckIn checkIn) {
        var taxi = taxis.require(taxiId);
        if (taxi.isActive()) {
            throw new TaxiCantBeCheckedInException(taxi);
        }

        var driverId = checkIn.getDriver();
        var driver = drivers.require(driverId);

        var latitude = checkIn.getLatitude();
        var longitude = checkIn.getLongitude();
        taxis.setDriver(taxiId, driver);
        taxis.setLocation(taxiId, latitude, longitude);
        taxis.updateStatus(taxiId, IDLE);
    }

    @Transactional(isolation = SERIALIZABLE)
    public void checkOut(UUID taxiId) {
        var taxi = taxis.require(taxiId);

        if (taxi.isBusy()) {
            throw new TaxiBusyException();
        }

        taxis.rmDriverFrom(taxi);
        taxis.resetLocation(taxiId);
        taxis.updateStatus(taxiId, OFF);
    }
}
