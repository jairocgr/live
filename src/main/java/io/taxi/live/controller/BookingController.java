package io.taxi.live.controller;

import io.taxi.live.domain.AcceptanceDetails;
import io.taxi.live.domain.Booking;
import io.taxi.live.domain.BookingStatus;
import io.taxi.live.domain.NewBooking;
import io.taxi.live.repository.BookingRepository;
import io.taxi.live.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class BookingController {

    private final BookingRepository bookings;
    private final BookingService service;

    @PostMapping("/bookings")
    public void add(@Valid @RequestBody NewBooking booking) {
        bookings.add(booking);
    }

    @GetMapping("/bookings")
    public List<Booking> list(@RequestParam(name="status",defaultValue="OPEN",required = false) BookingStatus status) {
        return bookings.all(status);
    }

    @PostMapping("/booking/{id}/accept")
    public void accept(@PathVariable(name = "id") UUID booking, @RequestBody @Valid AcceptanceDetails details) {
        service.accept(booking, details);
    }

    @PostMapping("/booking/{id}/finish")
    public void accept(@PathVariable(name = "id") UUID booking) {
        service.finish(booking);
    }
}
