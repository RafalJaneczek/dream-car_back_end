package pl.neverendingcode.exception;

import java.io.Serial;

public class CarNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public CarNotFoundException(String message) {
        super(message);
    }

}
