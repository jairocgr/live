package io.taxi.live.fixture;


import io.taxi.live.domain.Driver;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DriverFixture implements Fixture<Driver> {

    private Driver driver;

    public static DriverFixture builder() {
        return new DriverFixture();
    }

    public DriverFixture withFixedData() {
        driver = Driver.builder()
            .id(UUID.fromString("5390428f-5cdb-40c3-a8de-2d4ce4fc3779"))
            .name("William H. Tower")
            .build();
        return this;
    }

    public Driver build() {
        return driver;
    }

    public ResultSet buildResultSet() throws SQLException {
        var rs = mock(ResultSet.class);
        when(rs.getObject("id", UUID.class)).thenReturn(driver.getId());
        when(rs.getString("name")).thenReturn(driver.getName());
        return rs;
    }
}
