package bikeshop.service;

import bikeshop.domain.models.service.BicycleServiceModel;

import java.util.List;

public interface BicycleService {

    void addBicycle(BicycleServiceModel bicycleServiceModel);

    List<BicycleServiceModel> findAll();

    BicycleServiceModel findById(String id);
}
