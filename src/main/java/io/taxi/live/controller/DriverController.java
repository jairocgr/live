package io.taxi.live.controller;

import io.taxi.live.domain.Driver;
import io.taxi.live.repository.DriverRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DriverController {

    private final DriverRepository drivers;

    @PostMapping("/drivers")
    public void add(@Valid @RequestBody Driver driver) {
        drivers.add(driver);
    }
}
