package bikeshop.validation;

import bikeshop.domain.entities.Bicycle;

public interface BicycleValidationService {
    boolean isValid(Bicycle bicycle);
}
