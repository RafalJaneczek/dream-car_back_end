package pl.neverendingcode.vehicle.motorcycle.exception;

import java.io.Serial;

public class MotorcycleNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public MotorcycleNotFoundException(String message) {
        super(message);
    }

}
