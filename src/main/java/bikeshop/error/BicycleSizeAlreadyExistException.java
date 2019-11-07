package bikeshop.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Duplicate bicycle size")
public class BicycleSizeAlreadyExistException extends BaseException {

    public BicycleSizeAlreadyExistException(String message) {
        super(HttpStatus.CONFLICT.value(), message);
    }
}
