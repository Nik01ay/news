package example.news.error.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UnsupportedStateException extends RuntimeException {
    public UnsupportedStateException(String message) {
        super(message);
    }
}
