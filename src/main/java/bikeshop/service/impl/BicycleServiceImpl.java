package bikeshop.service.impl;

import bikeshop.common.Constants;
import bikeshop.domain.entities.Bicycle;
import bikeshop.domain.entities.BicycleSize;
import bikeshop.domain.entities.Category;
import bikeshop.domain.models.service.BicycleServiceModel;
import bikeshop.repository.BicycleRepository;
import bikeshop.repository.BicycleSizeRepository;
import bikeshop.repository.CategoryRepository;
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
    private final CategoryRepository categoryRepository;
    private final CategoryService categoryService;
    private final BicycleSizeRepository bicycleSizeRepository;
    private final ModelMapper mapper;

    @Autowired
    public BicycleServiceImpl(BicycleRepository bicycleRepository, CategoryRepository categoryRepository, CategoryService categoryService, BicycleSizeRepository bicycleSizeRepository, ModelMapper mapper) {
        this.bicycleRepository = bicycleRepository;
        this.categoryRepository = categoryRepository;
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
                .map(this::getBicycleServiceModel)
                .collect(Collectors.toList());
    }

    @Override
    public BicycleServiceModel findById(String id) {
        Bicycle bicycle = getBicycleById(id);

        return this.getBicycleServiceModel(bicycle);
    }

    @Override
    public void editById(String id, BicycleServiceModel model) {
        Bicycle bicycle = getBicycleById(id);

        bicycle.setMake(model.getMake());
        bicycle.setModel(model.getModel());
        bicycle.setColor(model.getColor());
        bicycle.setPrice(model.getPrice());
        bicycle.setDescription(model.getDescription());

        Category category = categoryRepository.findByName(model.getCategory());
        bicycle.setCategory(category);

        Set<BicycleSize> sizes = bicycleSizeRepository.findAll()
                .stream()
                .filter(c -> model.getBicycleSize().contains(c.getName()))
                .collect(Collectors.toSet());
        bicycle.setBicycleSize(sizes);

        bicycleRepository.save(bicycle);
    }

    @Override
    public void deleteBicycleById(String id) {
        Bicycle bicycle = getBicycleById(id);
        bicycleRepository.delete(bicycle);
    }

    @Override
    public List<BicycleServiceModel> findAllByCategory(String category) {
        return  bicycleRepository.findAllByCategoryName(category)
                .stream()
                .map(this::getBicycleServiceModel)
                .collect(Collectors.toList());
    }

    private BicycleServiceModel getBicycleServiceModel(Bicycle bicycle) {
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

    private Bicycle getBicycleById(String id) {
        return bicycleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(Constants.INCORRECT_ID));
    }
}
