package io.taxi.live.exception;

public class TaxiBusyException extends BusinessException {

    public TaxiBusyException() {
        super("Taxi is busy with a ongoing booking");
    }

}
