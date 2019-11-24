package bikeshop.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Invalid order id")
public class OrderNotFoundException extends BaseException {

    public OrderNotFoundException(String message) {
        super(HttpStatus.BAD_REQUEST.value(), message);
    }
}
