package pl.neverendingcode.security.exceptionHandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.neverendingcode.security.exception.RoleNotFoundException;
import pl.neverendingcode.security.exception.UserEmailException;
import pl.neverendingcode.security.exception.UsernameException;
import pl.neverendingcode.core.model.ErrorMessage;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class AuthenticationExceptionHandler {

    @ExceptionHandler({UsernameNotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessage userNotFound(UsernameNotFoundException exception) {
        log.error(exception.getMessage());
        return new ErrorMessage(HttpStatus.NOT_FOUND.value(), LocalDateTime.now(), exception.getMessage());
    }

    @ExceptionHandler({RoleNotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessage roleNotFound(RoleNotFoundException exception) {
        log.error(exception.getMessage());
        return new ErrorMessage(HttpStatus.NOT_FOUND.value(), LocalDateTime.now(), exception.getMessage());
    }

    @ExceptionHandler({UserEmailException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage userEmailException(UserEmailException exception) {
        log.error(exception.getMessage());
        return new ErrorMessage(HttpStatus.BAD_REQUEST.value(), LocalDateTime.now(), exception.getMessage());
    }

    @ExceptionHandler({UsernameException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage usernameException(UsernameException exception) {
        log.error(exception.getMessage());
        return new ErrorMessage(HttpStatus.BAD_REQUEST.value(), LocalDateTime.now(), exception.getMessage());
    }

}
