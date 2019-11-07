package bikeshop.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Duplicate bicycle")
public class BicycleAlreadyExistException extends BaseException {

    public BicycleAlreadyExistException(String message) {
        super(HttpStatus.CONFLICT.value(), message);
    }
}
