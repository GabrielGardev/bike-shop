package bikeshop.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Duplicate username")
public class UsernameAlreadyExistException extends BaseException {

    public UsernameAlreadyExistException(String message) {
        super(HttpStatus.CONFLICT.value(), message);
    }
}
