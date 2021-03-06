package bikeshop.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Invalid category id")
public class CategoryNotFoundException extends BaseException {

    public CategoryNotFoundException(String message) {
        super(HttpStatus.BAD_REQUEST.value(), message);
    }
}
