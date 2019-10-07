package bikeshop.service.impl;

import bikeshop.common.Constants;
import bikeshop.domain.entities.Role;
import bikeshop.domain.models.service.RoleServiceModel;
import bikeshop.repository.RoleRepository;
import bikeshop.service.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final ModelMapper mapper;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository, ModelMapper mapper) {
        this.roleRepository = roleRepository;
        this.mapper = mapper;
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
                .map(r -> mapper.map(r, RoleServiceModel.class))
                .collect(Collectors.toSet());
    }

    @Override
    public RoleServiceModel findByAuthority(String authority) {
        Role role = roleRepository.findByAuthority(authority);

        return mapper.map(role, RoleServiceModel.class);
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
