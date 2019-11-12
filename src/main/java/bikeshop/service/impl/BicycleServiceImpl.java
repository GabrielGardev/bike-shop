package bikeshop.service.impl;

import bikeshop.domain.entities.Bicycle;
import bikeshop.domain.entities.BicycleSize;
import bikeshop.domain.entities.Category;
import bikeshop.domain.models.service.BicycleServiceModel;
import bikeshop.error.BicycleAlreadyExistException;
import bikeshop.error.BicycleNotFoundException;
import bikeshop.error.CategoryNotFoundException;
import bikeshop.repository.BicycleRepository;
import bikeshop.repository.BicycleSizeRepository;
import bikeshop.repository.CategoryRepository;
import bikeshop.service.BicycleService;
import bikeshop.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import static bikeshop.common.Constants.*;

@Service
public class BicycleServiceImpl implements BicycleService {
    private static int BICYCLES_ON_DISCOUNT = 3;
    private static Double BICYCLES_DISCOUNT = 10d;
    private static Double MIN_BICYCLES_DISCOUNT = 0d;

    private final BicycleRepository bicycleRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryService categoryService;
    private final BicycleSizeRepository bicycleSizeRepository;
    private final ModelMapper mapper;

    @Autowired
    public BicycleServiceImpl(BicycleRepository bicycleRepository,
                              CategoryRepository categoryRepository,
                              CategoryService categoryService,
                              BicycleSizeRepository bicycleSizeRepository,
                              ModelMapper mapper) {
        this.bicycleRepository = bicycleRepository;
        this.categoryRepository = categoryRepository;
        this.categoryService = categoryService;
        this.bicycleSizeRepository = bicycleSizeRepository;
        this.mapper = mapper;
    }

    @Override
    public void addBicycle(BicycleServiceModel bicycleServiceModel) {
        checkIfBicycleAlreadyExist(bicycleServiceModel.getMake(),
                bicycleServiceModel.getModel(),
                bicycleServiceModel.getColor());

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
        Bicycle bicycle = this.getBicycleById(id);

        return this.getBicycleServiceModel(bicycle);
    }

    @Override
    public void editById(String id, BicycleServiceModel model) {
        Bicycle bicycle = this.getBicycleById(id);

        String editBicycleMake = model.getMake();
        String editBicycleModel = model.getModel();
        String editBicycleColor = model.getColor();

        if(!bicycle.getMake().equals(editBicycleMake)
                || !bicycle.getModel().equals(editBicycleModel)
                || !bicycle.getColor().equals(editBicycleColor)){
            this.checkIfBicycleAlreadyExist(editBicycleMake, editBicycleModel, editBicycleColor);
        }

        bicycle.setMake(editBicycleMake);
        bicycle.setModel(editBicycleModel);
        bicycle.setColor(editBicycleColor);
        bicycle.setPrice(model.getPrice());
        bicycle.setDescription(model.getDescription());

        Category category = categoryRepository.findByName(model.getCategory())
                .orElseThrow(() -> new CategoryNotFoundException(INCORRECT_CATEGORY));
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
        Bicycle bicycle = this.getBicycleById(id);
        bicycleRepository.delete(bicycle);
    }

    @Override
    public List<BicycleServiceModel> findAllByCategory(String category) {
        return  bicycleRepository.findAllByCategoryName(category)
                .stream()
                .map(this::getBicycleServiceModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<BicycleServiceModel> findAllOnPromo() {
        return bicycleRepository.findAllByDiscountIsGreaterThan(MIN_BICYCLES_DISCOUNT)
                .stream()
                .map(this::getBicycleServiceModel)
                .collect(Collectors.toList());
    }

    @Scheduled(fixedRate = 120000)
    private void generateDiscounts(){
       bicycleRepository.setAllDiscountsToZero();

        List<Bicycle> allBicycles = bicycleRepository.findAll();
        if (allBicycles.isEmpty()){
            return;
        }

        if (allBicycles.size() < BICYCLES_ON_DISCOUNT){
            BICYCLES_ON_DISCOUNT = allBicycles.size();
        }

        Random rnd = new Random();
        for (int i = 0; i < BICYCLES_ON_DISCOUNT; i++) {
            int position = rnd.nextInt(allBicycles.size());
            Bicycle bicycle = allBicycles.get(position);
            bicycle.setDiscount(BICYCLES_DISCOUNT);
            bicycleRepository.save(bicycle);
            allBicycles.remove(bicycle);
        }
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

    private Bicycle getBicycleById(String id){
        return bicycleRepository.findById(id)
                .orElseThrow(() -> new BicycleNotFoundException(INCORRECT_ID));
    }


    private void checkIfBicycleAlreadyExist(String make, String model, String color) {
        Bicycle bicycle = bicycleRepository.findByMakeAndModelAndColor(make, model, color)
                .orElse(null);

        if (bicycle != null){
            throw new BicycleAlreadyExistException(DUPLICATE_BICYCLE);
        }
    }
}
