package pl.neverendingcode.security.exception;

import java.io.Serial;

public class UserException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public UserException(String message) {
        super(message);
    }

}
