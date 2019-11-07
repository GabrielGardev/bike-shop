package bikeshop.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Invalid bicycleSize id or name")
public class BicycleSizeNotFoundException extends BaseException {

    public BicycleSizeNotFoundException(String message) {
        super(HttpStatus.BAD_REQUEST.value(), message);
    }
}
