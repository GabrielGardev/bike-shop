package bikeshop.service.impl;

import bikeshop.common.Constants;
import bikeshop.domain.entities.Role;
import bikeshop.domain.models.service.RoleServiceModel;
import bikeshop.error.AuthorityNotFoundException;
import bikeshop.repository.RoleRepository;
import bikeshop.service.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static bikeshop.common.Constants.*;


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
            roleRepository.save(new Role(ROLE_ROOT));
            roleRepository.save(new Role(ROLE_ADMIN));
            roleRepository.save(new Role(ROLE_MODERATOR));
            roleRepository.save(new Role(ROLE_USER));
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
        Role role = roleRepository.findByAuthority(authority)
                .orElseThrow(() -> new AuthorityNotFoundException(INCORRECT_AUTHORITY));

        return mapper.map(role, RoleServiceModel.class);
    }
}
