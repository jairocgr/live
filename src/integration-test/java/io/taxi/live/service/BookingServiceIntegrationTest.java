package io.taxi.live.service;

import io.taxi.live.BaseIntegrationTest;
import io.taxi.live.domain.AcceptanceDetails;
import io.taxi.live.repository.BookingRepository;
import io.taxi.live.repository.TaxiRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static io.taxi.live.domain.BookingStatus.FINISHED;
import static io.taxi.live.domain.BookingStatus.ONGOING;
import static io.taxi.live.domain.TaxiStatus.BUSY;
import static io.taxi.live.domain.TaxiStatus.IDLE;
import static org.assertj.core.api.Assertions.assertThat;

public class BookingServiceIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private BookingService service;

    @Autowired
    private BookingRepository bookings;

    @Autowired
    private TaxiRepository taxis;

    @Test
    void testAcceptAndOpenBooking() {
        // OPEN booking
        var bookingId = UUID.fromString("bc673784-ed14-4b32-9d65-080a588ece2b");

        // Driver Mark with a IDLE taxi
        var driverId = UUID.fromString("a5bbc872-93b3-4645-800f-289a40b52672");
        var taxiId = UUID.fromString("3c3a2571-1a4f-4914-9740-18ca3d88c359");

        var details = new AcceptanceDetails(driverId);

        service.accept(bookingId, details);

        var given = bookings.require(bookingId);

        assertThat(given).satisfies(booking -> {
           assertThat(booking.getStatus()).isEqualTo(ONGOING);

           var taxi = booking.getTaxi();
           assertThat(taxi.getId()).isEqualTo(taxiId);
           assertThat(taxi.getStatus()).isEqualTo(BUSY);
           assertThat(taxi.getDriver().getId()).isEqualTo(driverId);
        });
    }

    @Test
    void testFinishOngoingBooking() {
        // ONGOING booking driven by Anthony
        var bookingId = UUID.fromString("95545353-0a0a-439b-b202-dea161235b8c");

        // Driver Anthony with a BUSY taxi
        var driverId = UUID.fromString("beacff30-31e7-426f-951c-fdfe75c6f1b3");

        service.finish(bookingId);

        // Check if booking is proper finished
        var booking = bookings.require(bookingId);
        assertThat(booking.getStatus()).isEqualTo(FINISHED);
        assertThat(booking.getTaxi()).isNull();

        // Check if taxi became IDLE again
        var taxi = taxis.requireByDriverId(driverId);
        assertThat(taxi.getStatus()).isEqualTo(IDLE);
        assertThat(taxi.getDriver().getId()).isEqualTo(driverId);
    }
}
