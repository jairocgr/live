package io.taxi.live.repository;

import io.taxi.live.BaseIntegrationTest;
import io.taxi.live.domain.Booking;
import io.taxi.live.exception.BookingNotFoundException;
import io.taxi.live.fixture.BookingFixture;
import io.taxi.live.fixture.NewBookingFixture;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static io.taxi.live.domain.BookingStatus.OPEN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BookingRepositoryIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private BookingRepository repository;

    @Test
    void shouldAddNewBooking() {
        var newBooking = NewBookingFixture.builder()
            .withFixedData()
            .build();

        var expected = BookingFixture.builder()
            .withFixedData()
            .withOpenStatus()
            .build();

        repository.add(newBooking);

        var given = repository.require(newBooking.getId());

        assertThat(given)
            .usingRecursiveComparison()
            .ignoringFields("createdAt")
            .isEqualTo(expected);
    }

    @Test
    void shouldListBookingsByStatus() {
        var bookings = repository.all(OPEN);

        assertThat(bookings)
            .hasSize(3)
            .hasOnlyElementsOfType(Booking.class)
            .allSatisfy(booking -> {
                assertThat(booking.getStatus()).isEqualTo(OPEN);
            });
    }

    @Test
    void shouldFailWhenRecordNotFound() {
        var unknownId = UUID.fromString("50680ac0-076f-4b21-9c54-575101d4f491");
        assertThrows(BookingNotFoundException.class, () -> repository.require(unknownId));
    }

}
