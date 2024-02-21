package io.taxi.live.controller;

import io.taxi.live.domain.CheckIn;
import io.taxi.live.domain.NewTaxi;
import io.taxi.live.domain.Taxi;
import io.taxi.live.domain.TaxiStatus;
import io.taxi.live.repository.TaxiRepository;
import io.taxi.live.service.TaxiService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class TaxiController {

    private final TaxiRepository taxis;
    private final TaxiService service;

    @PostMapping("/taxis")
    public void add(@Valid @RequestBody NewTaxi taxi) {
        taxis.add(taxi);
    }

    @GetMapping("/taxis")
    public List<Taxi> list(@RequestParam(name="status",defaultValue="IDLE",required = false) TaxiStatus status) {
        return taxis.all(status);
    }

    @PostMapping("/taxi/{id}/check-in")
    public void checkIn(@PathVariable(name = "id") UUID taxiId, @RequestBody @Valid CheckIn checkIn) {
        service.checkIn(taxiId, checkIn);
    }

    @PostMapping("/taxi/{id}/check-out")
    public void checkOut(@PathVariable(name = "id") UUID taxiId) {
        service.checkOut(taxiId);
    }

}
