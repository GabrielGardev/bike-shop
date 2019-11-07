package bikeshop.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Duplicate category")
public class CategoryAlreadyExistException extends BaseException {

    public CategoryAlreadyExistException(String message) {
        super(HttpStatus.CONFLICT.value(), message);
    }
}
