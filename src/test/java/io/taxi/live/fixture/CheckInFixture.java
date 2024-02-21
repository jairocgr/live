package io.taxi.live.fixture;


import io.taxi.live.domain.CheckIn;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CheckInFixture implements Fixture<CheckIn> {

    private CheckIn checkIn;

    public static CheckInFixture builder() {
        return new CheckInFixture();
    }

    public CheckInFixture withFixedData() {
        var driver = DriverFixture.builder()
            .withFixedData()
            .build();
        checkIn = CheckIn.builder()
            .driver(driver.getId())
            .latitude(42.322665961158904d)
            .longitude(-71.0853777121208d)
            .build();
        return this;
    }

    public CheckInFixture withDriver(UUID driverId) {
        checkIn = checkIn.toBuilder()
            .driver(driverId)
            .build();
        return this;
    }

    public CheckIn build() {
        return checkIn;
    }
}
