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
    public UserServiceModel registerUser(UserServiceModel userServiceModel) {
        roleService.seedRolesInDb();
        this.putProperRoles(userServiceModel);

        User user = modelMapper.map(userServiceModel, User.class);
        user.setPassword(bCryptPasswordEncoder.encode(userServiceModel.getPassword()));

        return modelMapper.map(user, UserServiceModel.class);
    }

    //danger method check if service model is without authorities
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
