package bikeshop.service.impl;

import bikeshop.common.Constants;
import bikeshop.domain.entities.Bicycle;
import bikeshop.domain.entities.BicycleSize;
import bikeshop.domain.entities.Category;
import bikeshop.domain.models.service.BicycleServiceModel;
import bikeshop.repository.BicycleRepository;
import bikeshop.repository.BicycleSizeRepository;
import bikeshop.service.BicycleService;
import bikeshop.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BicycleServiceImpl implements BicycleService {

    private final BicycleRepository bicycleRepository;
    private final CategoryService categoryService;
    private final BicycleSizeRepository bicycleSizeRepository;
    private final ModelMapper mapper;

    @Autowired
    public BicycleServiceImpl(BicycleRepository bicycleRepository, CategoryService categoryService, BicycleSizeRepository bicycleSizeRepository, ModelMapper mapper) {
        this.bicycleRepository = bicycleRepository;
        this.categoryService = categoryService;
        this.bicycleSizeRepository = bicycleSizeRepository;
        this.mapper = mapper;
    }

    @Override
    public void addBicycle(BicycleServiceModel bicycleServiceModel) {
        Bicycle bicycle = mapper
                .map(bicycleServiceModel, Bicycle.class);
        Category category = mapper
                .map(categoryService.findById(bicycleServiceModel.getCategory()), Category.class);
        Set<BicycleSize> sizes =
                new HashSet<>(bicycleSizeRepository.findAllById(bicycleServiceModel.getBicycleSize()));

        bicycle.setCategory(category);
        bicycle.setBicycleSize(sizes);

        bicycleRepository.save(bicycle);
    }

    @Override
    public List<BicycleServiceModel> findAll() {
        return bicycleRepository.findAll()
                .stream()
                .map(bike -> {
                    BicycleServiceModel serviceModel = mapper.map(bike, BicycleServiceModel.class);
                    serviceModel.setCategory(bike.getCategory().getName());

                    serviceModel.setBicycleSize(this.getSizes(bike));
                    return serviceModel;
                })
                .collect(Collectors.toList());
    }

    @Override
    public BicycleServiceModel findById(String id) {
        Bicycle bicycle = bicycleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(Constants.INCORRECT_ID));

        BicycleServiceModel serviceModel = mapper.map(bicycle, BicycleServiceModel.class);
        serviceModel.setCategory(bicycle.getCategory().getName());
        serviceModel.setBicycleSize(this.getSizes(bicycle));

        return serviceModel;
    }

    private Set<String> getSizes(Bicycle bike) {
        return bike.getBicycleSize()
                .stream()
                .map(BicycleSize::getName)
                .collect(Collectors.toSet());
    }
}
