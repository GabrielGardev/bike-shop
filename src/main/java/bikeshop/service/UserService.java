package bikeshop.service;

import bikeshop.domain.models.service.UserServiceModel;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    UserServiceModel registerUser(UserServiceModel userServiceModel);
}
