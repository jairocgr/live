package io.taxi.live.fixture;


import io.taxi.live.domain.Taxi;
import io.taxi.live.json.JsonSerializer;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import static io.taxi.live.domain.TaxiStatus.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TaxiFixture implements Fixture<Taxi> {

    private Taxi taxi;

    public static TaxiFixture builder() {
        return new TaxiFixture();
    }

    public TaxiFixture withFixedData() {
        var driver = DriverFixture.builder()
            .withFixedData()
            .build();

        taxi = Taxi.builder()
            .id(UUID.fromString("1366c950-54f6-4e2f-8493-e3e808e7c286"))
            .brand("Volkswagen")
            .model("Virtus TSI")
            .registration("AK 607 SDK")
            .status(IDLE)
            .latitude(42.2925427212d) // Boston location
            .longitude(-71.070664592d)
            .driver(driver)
            .build();
        return this;
    }

    public TaxiFixture withBusyStatus() {
        taxi = taxi.toBuilder()
            .status(BUSY)
            .build();
        return this;
    }

    public TaxiFixture withOffDutyStatus() {
        taxi = taxi.toBuilder()
            .status(OFF)
            .driver(null)
            .latitude(null)
            .longitude(null)
            .build();
        return this;
    }

    public Taxi build() {
        return taxi;
    }

    public ResultSet buildResultSet() throws SQLException {
        var serializer = new JsonSerializer();
        var rs = mock(ResultSet.class);
        when(rs.getObject("id", UUID.class)).thenReturn(taxi.getId());
        when(rs.getString("brand")).thenReturn(taxi.getBrand());
        when(rs.getString("model")).thenReturn(taxi.getModel());
        when(rs.getString("registration")).thenReturn(taxi.getRegistration());
        when(rs.getString("status")).thenReturn(taxi.getStatus().toString());

        if (taxi.getLatitude() == null) {
            when(rs.getDouble("latitude")).thenReturn(0d);
            when(rs.wasNull()).thenReturn(true);
        } else {
            when(rs.getDouble("latitude")).thenReturn(taxi.getLatitude());
        }

        if (taxi.getLongitude() == null) {
            when(rs.getDouble("longitude")).thenReturn(0d);
            when(rs.wasNull()).thenReturn(true);
        } else {
            when(rs.getDouble("longitude")).thenReturn(taxi.getLongitude());
        }

        when(rs.getString("driver")).thenReturn(serializer.toJson(taxi.getDriver()));
        return rs;
    }
}
