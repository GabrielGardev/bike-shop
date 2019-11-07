package bikeshop.validation.impl;

import bikeshop.domain.entities.User;
import bikeshop.validation.UserValidationService;
import org.springframework.stereotype.Component;

@Component
public class UserValidationServiceImpl implements UserValidationService {
    @Override
    public boolean isValid(User user) {
        return user == null;
    }
}
