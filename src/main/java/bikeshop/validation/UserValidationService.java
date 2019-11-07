package bikeshop.validation;

import bikeshop.domain.entities.User;

public interface UserValidationService {
    boolean isValid(User user);
}
