package io.taxi.live.repository.mapper;

import io.taxi.live.domain.Driver;
import io.taxi.live.domain.Taxi;
import io.taxi.live.domain.TaxiStatus;
import io.taxi.live.json.JsonSerializer;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@RequiredArgsConstructor
public class TaxiRowMapper implements RowMapper<Taxi> {

    private final JsonSerializer serializer;

    @Override
    public Taxi mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Taxi.builder()
            .id(rs.getObject("id", UUID.class))
            .brand(rs.getString("brand"))
            .model(rs.getString("model"))
            .registration(rs.getString("registration"))
            .status(getStatus(rs))
            .latitude(getDoubleOrNull(rs, "latitude"))
            .longitude(getDoubleOrNull(rs, "longitude"))
            .driver(fromJson(rs.getString("driver"), Driver.class))
            .build();
    }

    private Double getDoubleOrNull(ResultSet rs, String column) throws SQLException {
        var val = rs.getDouble(column);
        return rs.wasNull() ? null : val;
    }

    private TaxiStatus getStatus(ResultSet rs) throws SQLException {
        var status = rs.getString("status");
        return TaxiStatus.valueOf(status);
    }

    public <T> T fromJson(final String json, Class<T> clazz) {
        return json == null ? null : serializer.fromJson(json, clazz);
    }
}
