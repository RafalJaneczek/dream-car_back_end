package pl.neverendingcode.exceptionHandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.neverendingcode.exception.CarNotFoundException;
import pl.neverendingcode.model.ErrorMessage;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class CarExceptionHandler {

    @ExceptionHandler({CarNotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessage carNotFound(CarNotFoundException exception) {
        log.error(exception.getMessage());
        return new ErrorMessage(HttpStatus.NOT_FOUND.value(), LocalDateTime.now(), exception.getMessage());
    }

}
