package bikeshop.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = "Passwords dont match")
public class PasswordDontMatchException extends BaseException {

    public PasswordDontMatchException(String message) {
        super(HttpStatus.UNAUTHORIZED.value(), message);
    }
}
