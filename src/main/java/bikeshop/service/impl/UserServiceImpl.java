package bikeshop.service.impl;

import bikeshop.domain.entities.User;
import bikeshop.domain.models.service.RoleServiceModel;
import bikeshop.domain.models.service.UserServiceModel;
import bikeshop.error.EmailAlreadyExistException;
import bikeshop.error.PasswordDontMatchException;
import bikeshop.error.UserNotFoundException;
import bikeshop.error.UsernameAlreadyExistException;
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

import static bikeshop.common.Constants.*;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final ModelMapper mapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           RoleService roleService,
                           ModelMapper mapper,
                           BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.mapper = mapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void registerUser(UserServiceModel userServiceModel) {

        this.checkIfUserExistByUsernameOrEmail(userServiceModel.getUsername(), userServiceModel.getEmail());

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

        this.checkIfPasswordsMatch(oldPassword, user);
        this.checkIfEmailAlreadyExist(user.getEmail(), userServiceModel.getEmail());

        user.setPassword(userServiceModel.getPassword().isEmpty() ?
                user.getPassword() :
                bCryptPasswordEncoder.encode(userServiceModel.getPassword()));
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
                .orElseThrow(() -> new UserNotFoundException(INCORRECT_ID));

        UserServiceModel userServiceModel = mapper.map(user, UserServiceModel.class);
        userServiceModel.getAuthorities().clear();

        switch (role) {
            case "user":
                userServiceModel.getAuthorities().add(roleService.findByAuthority(ROLE_USER));
                break;
            case "moderator":
                userServiceModel.getAuthorities().add(roleService.findByAuthority(ROLE_USER));
                userServiceModel.getAuthorities().add(roleService.findByAuthority(ROLE_MODERATOR));
                break;
            case "admin":
                userServiceModel.getAuthorities().add(roleService.findByAuthority(ROLE_USER));
                userServiceModel.getAuthorities().add(roleService.findByAuthority(ROLE_MODERATOR));
                userServiceModel.getAuthorities().add(roleService.findByAuthority(ROLE_ADMIN));
                break;
        }

        this.userRepository.saveAndFlush(mapper.map(userServiceModel, User.class));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(USERNAME_NOT_FOUND));
    }

    private void putProperRoles(UserServiceModel userServiceModel) {
        if (userRepository.count() == 0){
            userServiceModel.setAuthorities(roleService.findAllRoles());
        }else {
            RoleServiceModel role = roleService.findByAuthority(ROLE_USER);
            userServiceModel.setAuthorities(new LinkedHashSet<>());
            userServiceModel.getAuthorities().add(role);
        }
    }

    private void checkIfUserExistByUsernameOrEmail(String username, String email) {
        User userInDb = userRepository.findByUsername(username).orElse(null);

        if (userInDb != null) {
            throw new UsernameAlreadyExistException(DUPLICATE_USERNAME);
        }
        User userInDbWithSameEmail = userRepository.findByEmail(email).orElse(null);

        if (userInDbWithSameEmail != null) {
            throw new EmailAlreadyExistException(DUPLICATE_EMAIL);
        }
    }

    private void checkIfPasswordsMatch(String oldPassword, User user) {
        if (!bCryptPasswordEncoder.matches(oldPassword, user.getPassword())) {
            throw new PasswordDontMatchException(PASSWORDS_DONT_MATCH);
        }
    }

    private void checkIfEmailAlreadyExist(String oldEmail, String newEmail) {
        if (!oldEmail.equals(newEmail)) {
            User userInDbWithSameEmail = userRepository.findByEmail(newEmail).orElse(null);

            if (userInDbWithSameEmail != null) {
                throw new EmailAlreadyExistException(DUPLICATE_EMAIL);
            }
        }
    }
}
