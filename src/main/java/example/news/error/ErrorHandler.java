package example.news.error;

import example.news.error.exceptions.BadRequredException;
import example.news.error.exceptions.ObjectNotFoundException;
import example.news.error.exceptions.UnsupportedStateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handlerNotFoundException(final ObjectNotFoundException e) {
        log.warn("404 {}", e.getMessage(), e);
        return new ErrorResponse("Object not found 404", e.getMessage()) {
        };
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handlerBadRequest(final BadRequredException e) {
        log.warn("400 {}", e.getMessage(), e);
        return new ErrorResponse("Object not available 400 ", e.getMessage());
    }

    @ExceptionHandler(UnsupportedStateException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handlerUnsupportedState(final UnsupportedStateException exception) {
        log.warn("403 {}", exception.getMessage(), exception);
        return new ErrorResponse(exception.getMessage(), exception.getMessage());
    }
}