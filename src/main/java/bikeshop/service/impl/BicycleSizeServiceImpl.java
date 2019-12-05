package bikeshop.service.impl;

import bikeshop.domain.entities.BicycleSize;
import bikeshop.domain.models.service.BicycleSizeServiceModel;
import bikeshop.error.BicycleSizeAlreadyExistException;
import bikeshop.error.BicycleSizeNotFoundException;
import bikeshop.repository.BicycleSizeRepository;
import bikeshop.service.BicycleSizeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static bikeshop.common.Constants.DUPLICATE_BICYCLE_SIZE;
import static bikeshop.common.Constants.INCORRECT_ID;

@Service
public class BicycleSizeServiceImpl implements BicycleSizeService {

    private final BicycleSizeRepository bicycleSizeRepository;
    private final ModelMapper mapper;

    @Autowired
    public BicycleSizeServiceImpl(BicycleSizeRepository bicycleSizeRepository, ModelMapper mapper) {
        this.bicycleSizeRepository = bicycleSizeRepository;
        this.mapper = mapper;
    }

    @Override
    public void addSize(BicycleSizeServiceModel bicycleSizeServiceModel) {
        this.checkIfSizeAlreadyExist(bicycleSizeServiceModel.getName());
        BicycleSize bicycleSize = mapper.map(bicycleSizeServiceModel, BicycleSize.class);
        bicycleSizeRepository.save(bicycleSize);
    }

    @Override
    public List<BicycleSizeServiceModel> findAllBicycleSizes() {
        return bicycleSizeRepository.findAll()
                .stream()
                .map(s -> mapper.map(s, BicycleSizeServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public BicycleSizeServiceModel findById(String id) {
        BicycleSize bicycleSize = this.getBicycleSize(id);
        return mapper.map(bicycleSize, BicycleSizeServiceModel.class);
    }

    @Override
    public void deleteBicycleSizeById(String id) {
        BicycleSize bicycleSize = this.getBicycleSize(id);
        bicycleSizeRepository.delete(bicycleSize);
    }

    private BicycleSize getBicycleSize(String id) {
        return bicycleSizeRepository.findById(id)
                .orElseThrow(() -> new BicycleSizeNotFoundException(INCORRECT_ID));
    }


    private void checkIfSizeAlreadyExist(String name) {
        BicycleSize bicycleSize = bicycleSizeRepository.findByName(name).orElse(null);

        if (bicycleSize != null){
            throw new BicycleSizeAlreadyExistException(DUPLICATE_BICYCLE_SIZE);
        }
    }
}
