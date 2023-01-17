package pl.neverendingcode.security.exception;

import java.io.Serial;

public class UserEmailException extends UserException {

    @Serial
    private static final long serialVersionUID = 1L;

    public UserEmailException(String message) {
        super(message);
    }
}
