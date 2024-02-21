package io.taxi.live.domain;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record AcceptanceDetails(@NotNull UUID driver) {}
