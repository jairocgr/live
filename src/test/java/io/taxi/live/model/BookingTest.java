package io.taxi.live.model;

import io.taxi.live.fixture.BookingFixture;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BookingTest {

    @Test
    void checkOpenBooking() {
        var booking = BookingFixture.builder()
            .withFixedData()
            .withOpenStatus()
            .build();

        assertTrue(booking.canBeAccepted());
        assertFalse(booking.canBeFinished());
    }

    @Test
    void checkOngoingBooking() {
        var booking = BookingFixture.builder()
            .withFixedData()
            .build();

        assertFalse(booking.canBeAccepted());
        assertTrue(booking.canBeFinished());
    }

    @Test
    void checkFinishedBooking() {
        var booking = BookingFixture.builder()
            .withFixedData()
            .finishedBooking()
            .build();

        assertFalse(booking.canBeAccepted());
        assertFalse(booking.canBeFinished());
    }
}
