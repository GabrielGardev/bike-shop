package bikeshop.service;

import bikeshop.domain.models.service.BicycleServiceModel;
import bikeshop.error.BicycleNotFoundException;

import java.util.List;

public interface BicycleService {

    void addBicycle(BicycleServiceModel bicycleServiceModel);

    List<BicycleServiceModel> findAll();

    BicycleServiceModel findById(String id);

    void editById(String id, BicycleServiceModel model);

    void deleteBicycleById(String id);

    List<BicycleServiceModel> findAllByCategory(String category);
}
