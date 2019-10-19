package bikeshop.service.impl;

import bikeshop.domain.entities.Component;
import bikeshop.domain.models.service.ComponentServiceModel;
import bikeshop.repository.ComponentRepository;
import bikeshop.service.ComponentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComponentServiceImpl implements ComponentService {

    private final ComponentRepository componentRepository;
    private final ModelMapper mapper;

    @Autowired
    public ComponentServiceImpl(ComponentRepository componentRepository, ModelMapper mapper) {
        this.componentRepository = componentRepository;
        this.mapper = mapper;
    }

    @Override
    public ComponentServiceModel saveComponent(ComponentServiceModel componentServiceModel) {
        String type = componentServiceModel.getType().trim();
        String description = componentServiceModel.getDescription().trim();
        Component component1 = componentRepository.findByTypeAndDescription(type, description)
                .orElse(null);
        if (component1 != null){
            return mapper.map(component1, ComponentServiceModel.class);
        }
        Component component = mapper.map(componentServiceModel, Component.class);
        return mapper.map(componentRepository.save(component), ComponentServiceModel.class);
    }
}
