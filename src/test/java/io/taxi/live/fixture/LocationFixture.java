package io.taxi.live.fixture;


import io.taxi.live.domain.Location;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LocationFixture implements Fixture<Location> {

    private Location location;

    public static LocationFixture builder() {
        return new LocationFixture();
    }

    public LocationFixture withFixedData() {
        location = Location.builder()
            .address("37 Rockland St")
            .city("Boston")
            .state("MA")
            .country("USA")
            .latitude(42.322665961158904d)
            .longitude(-71.0853777121208d)
            .build();
        return this;
    }

    public Location build() {
        return location;
    }
}
