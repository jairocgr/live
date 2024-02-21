package io.taxi.live.fixture;


import io.taxi.live.domain.NewBooking;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NewBookingFixture implements Fixture<NewBooking> {

    private NewBooking newBooking;

    public static NewBookingFixture builder() {
        return new NewBookingFixture();
    }

    public NewBookingFixture withFixedData() {
        var booking = BookingFixture.builder()
            .withFixedData()
            .build();

        newBooking = NewBooking.builder()
            .id(booking.getId())
            .client(booking.getClient())
            .destination(booking.getDestination())
            .origin(booking.getOrigin())
            .build();

        return this;
    }

    public NewBooking build() {
        return newBooking;
    }

}
