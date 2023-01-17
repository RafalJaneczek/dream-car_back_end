package pl.neverendingcode.security.exception;

import java.io.Serial;

public class UsernameException extends UserException {

    @Serial
    private static final long serialVersionUID = 1L;

    public UsernameException(String message) {
        super(message);
    }
}
