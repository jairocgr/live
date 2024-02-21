package io.taxi.live.fixture;


import io.taxi.live.domain.NewTaxi;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NewTaxiFixture implements Fixture<NewTaxi> {

    private NewTaxi newTaxi;

    public static NewTaxiFixture builder() {
        return new NewTaxiFixture();
    }

    public NewTaxiFixture withFixedData() {
        var driver = DriverFixture.builder()
            .withFixedData()
            .build();

        var taxi = TaxiFixture.builder()
            .withFixedData()
            .build();

        newTaxi = NewTaxi.builder()
            .id(taxi.getId())
            .brand(taxi.getBrand())
            .model(taxi.getModel())
            .registration(taxi.getRegistration())
            .build();

        return this;
    }

    public NewTaxi build() {
        return newTaxi;
    }

}
