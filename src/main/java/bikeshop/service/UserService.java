package bikeshop.service;

import bikeshop.domain.models.service.UserServiceModel;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    void registerUser(UserServiceModel userServiceModel);

    UserServiceModel findUserByUsername(String username);

    void editUserProfile(UserServiceModel userServiceModel, String oldPassword);
}
