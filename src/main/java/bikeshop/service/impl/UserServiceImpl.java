package bikeshop.service.impl;

import bikeshop.common.Constants;
import bikeshop.domain.entities.User;
import bikeshop.domain.models.service.RoleServiceModel;
import bikeshop.domain.models.service.UserServiceModel;
import bikeshop.repository.UserRepository;
import bikeshop.service.RoleService;
import bikeshop.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleService roleService, ModelMapper modelMapper, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.modelMapper = modelMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void registerUser(UserServiceModel userServiceModel) {
        roleService.seedRolesInDb();
        this.putProperRoles(userServiceModel);

        User user = modelMapper.map(userServiceModel, User.class);
        user.setPassword(bCryptPasswordEncoder.encode(userServiceModel.getPassword()));
        userRepository.save(user);
    }

    @Override
    public UserServiceModel findUserByUsername(String username) {
        User user = (User) this.loadUserByUsername(username);
        return modelMapper.map(user, UserServiceModel.class);
    }

    @Override
    public void editUserProfile(UserServiceModel userServiceModel, String oldPassword) {
        User user = (User) this.loadUserByUsername(userServiceModel.getUsername());

        if (!this.bCryptPasswordEncoder.matches(oldPassword, user.getPassword())) {
            throw new IllegalArgumentException(Constants.INCORRECT_PASSWORD);
        }

        user.setPassword(userServiceModel.getPassword().isEmpty() ?
                user.getPassword() :
                this.bCryptPasswordEncoder.encode(userServiceModel.getPassword()));
        user.setEmail(userServiceModel.getEmail());
        user.setFirstName(userServiceModel.getFirstName());
        user.setLastName(userServiceModel.getLastName());
        userRepository.save(user);
    }


    private void putProperRoles(UserServiceModel userServiceModel) {
        if (userRepository.count() == 0){
            userServiceModel.setAuthorities(roleService.findAllRoles());
        }else {
            RoleServiceModel role = roleService.findByAuthority(Constants.ROLE_USER);
            userServiceModel.setAuthorities(new LinkedHashSet<>());
            userServiceModel.getAuthorities().add(role);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(Constants.USERNAME_NOT_FOUND));
    }
}
