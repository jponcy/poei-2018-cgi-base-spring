package hello.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

    /** Serial number. */
    private static final long serialVersionUID = -5949787739887438683L;
}
