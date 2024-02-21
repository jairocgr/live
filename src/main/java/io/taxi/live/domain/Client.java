package io.taxi.live.domain;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Client {

    @NotNull
    @Size(min=2, max = 128)
    private final String name;

    @NotNull
    @Pattern(regexp = "^\\+([0-9]{6,32})$")
    private final String phone;
}
