package bikeshop.service.impl;

import bikeshop.domain.entities.Component;
import bikeshop.domain.models.service.ComponentServiceModel;
import bikeshop.error.ComponentNotFoundException;
import bikeshop.repository.ComponentRepository;
import bikeshop.service.ComponentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static bikeshop.common.Constants.INCORRECT_ID;

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
        Component component = componentRepository.findByTypeAndDescription(type, description)
                .orElse(null);
        if (component != null){
            return mapper.map(component, ComponentServiceModel.class);
        }
        component = mapper.map(componentServiceModel, Component.class);
        return mapper.map(componentRepository.save(component), ComponentServiceModel.class);
    }

    @Override
    public ComponentServiceModel editById(String id, String description) {
        Component component = componentRepository.findById(id)
                .orElseThrow(() -> new ComponentNotFoundException(INCORRECT_ID));
        component.setDescription(description);

        Component result = componentRepository.save(component);
        return mapper.map(result, ComponentServiceModel.class);
    }
}
