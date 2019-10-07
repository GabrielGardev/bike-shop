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
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final ModelMapper mapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleService roleService, ModelMapper mapper, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.mapper = mapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void registerUser(UserServiceModel userServiceModel) {
        roleService.seedRolesInDb();
        this.putProperRoles(userServiceModel);

        User user = mapper.map(userServiceModel, User.class);
        user.setPassword(bCryptPasswordEncoder.encode(userServiceModel.getPassword()));
        userRepository.save(user);
    }

    @Override
    public UserServiceModel findUserByUsername(String username) {
        User user = (User) this.loadUserByUsername(username);
        return mapper.map(user, UserServiceModel.class);
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

    @Override
    public List<UserServiceModel> findAll() {
        return userRepository.findAll()
                .stream()
                .map(u -> mapper.map(u, UserServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public void setUserRole(String id, String role) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(Constants.INCORRECT_ID));

        UserServiceModel userServiceModel = mapper.map(user, UserServiceModel.class);
        userServiceModel.getAuthorities().clear();

        switch (role) {
            case "user":
                userServiceModel.getAuthorities().add(roleService.findByAuthority(Constants.ROLE_USER));
                break;
            case "moderator":
                userServiceModel.getAuthorities().add(roleService.findByAuthority(Constants.ROLE_USER));
                userServiceModel.getAuthorities().add(roleService.findByAuthority(Constants.ROLE_MODERATOR));
                break;
            case "admin":
                userServiceModel.getAuthorities().add(roleService.findByAuthority(Constants.ROLE_USER));
                userServiceModel.getAuthorities().add(roleService.findByAuthority(Constants.ROLE_MODERATOR));
                userServiceModel.getAuthorities().add(roleService.findByAuthority(Constants.ROLE_ADMIN));
                break;
        }

        this.userRepository.saveAndFlush(mapper.map(userServiceModel, User.class));
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
