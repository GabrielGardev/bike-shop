package bikeshop.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Invalid authority name")
public class AuthorityNotFoundException extends BaseException {

    public AuthorityNotFoundException(String message) {
        super(HttpStatus.BAD_REQUEST.value(), message);
    }
}
