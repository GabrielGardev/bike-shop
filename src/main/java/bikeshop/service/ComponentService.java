package bikeshop.service;

import bikeshop.domain.models.service.ComponentServiceModel;

public interface ComponentService {

    ComponentServiceModel saveComponent(ComponentServiceModel componentServiceModel);

    ComponentServiceModel editById(String id, String description);
}
