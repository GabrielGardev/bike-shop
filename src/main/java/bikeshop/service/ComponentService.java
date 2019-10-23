package bikeshop.service;

import bikeshop.domain.models.service.ComponentServiceModel;

public interface ComponentService {

    ComponentServiceModel saveComponent(ComponentServiceModel componentServiceModel);

    void editById(String id, String description);
}
