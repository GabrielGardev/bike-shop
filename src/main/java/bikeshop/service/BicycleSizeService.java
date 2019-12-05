package bikeshop.service;

import bikeshop.domain.models.service.BicycleSizeServiceModel;

import java.util.List;

public interface BicycleSizeService {

    void addSize(BicycleSizeServiceModel bicycleSizeServiceModel);

    List<BicycleSizeServiceModel> findAllBicycleSizes();

    BicycleSizeServiceModel findById(String id);

    void deleteBicycleSizeById(String id);
}
