package bikeshop.validation.impl;

import bikeshop.domain.entities.Bicycle;
import bikeshop.validation.BicycleValidationService;
import org.springframework.stereotype.Component;

@Component
public class BicycleValidationServiceImpl implements BicycleValidationService {
    @Override
    public boolean isValid(Bicycle bicycle) {
        return bicycle == null;
    }
}
