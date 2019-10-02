package bikeshop.service.impl;

import bikeshop.common.Constants;
import bikeshop.domain.entities.Role;
import bikeshop.domain.models.service.RoleServiceModel;
import bikeshop.repository.RoleRepository;
import bikeshop.service.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository, ModelMapper modelMapper) {
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedRolesInDb() {
        if (roleRepository.count() == 0){
            roleRepository.saveAll(createAllRoles());
        }
    }

    @Override
    public Set<RoleServiceModel> findAllRoles() {
        return roleRepository
                .findAll()
                .stream()
                .map(r -> modelMapper.map(r, RoleServiceModel.class))
                .collect(Collectors.toSet());
    }

    @Override
    public RoleServiceModel findByAuthority(String authority) {
        Role role = roleRepository.findByAuthority(authority);

        return modelMapper.map(role, RoleServiceModel.class);
    }

    private List<Role> createAllRoles() {
        List<Role> roles = new ArrayList<>();
        roles.add(new Role(Constants.ROLE_ROOT));
        roles.add(new Role(Constants.ROLE_ADMIN));
        roles.add(new Role(Constants.ROLE_MODERATOR));
        roles.add(new Role(Constants.ROLE_USER));
        return roles;
    }
}
