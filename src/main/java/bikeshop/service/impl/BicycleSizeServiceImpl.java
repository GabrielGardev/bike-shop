package bikeshop.service.impl;

import bikeshop.common.Constants;
import bikeshop.domain.entities.BicycleSize;
import bikeshop.domain.models.service.BicycleSizeServiceModel;
import bikeshop.repository.BicycleSizeRepository;
import bikeshop.service.BicycleSizeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
        BicycleSize bicycleSize = mapper.map(bicycleSizeServiceModel, BicycleSize.class);
        bicycleSizeRepository.saveAndFlush(bicycleSize);
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

    private BicycleSize getBicycleSize(String id) {
        return bicycleSizeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(Constants.INCORRECT_ID));
    }
}
