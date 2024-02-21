package io.taxi.live.fixture;


import io.taxi.live.domain.Client;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClientFixture implements Fixture<Client> {

    private Client client;

    public static ClientFixture builder() {
        return new ClientFixture();
    }

    public ClientFixture withFixedData() {
        client = Client.builder()
            .name("John Smith")
            .phone("+151262544922")
            .build();
        return this;
    }

    public Client build() {
        return client;
    }
}
