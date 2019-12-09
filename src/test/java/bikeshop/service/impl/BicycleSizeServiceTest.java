package bikeshop.service.impl;

import bikeshop.domain.entities.BicycleSize;
import bikeshop.domain.models.service.BicycleSizeServiceModel;
import bikeshop.error.BicycleSizeAlreadyExistException;
import bikeshop.error.BicycleSizeNotFoundException;
import bikeshop.repository.BicycleSizeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BicycleSizeServiceTest {

    private BicycleSizeServiceModel bicycleSizeServiceModel = new BicycleSizeServiceModel();
    private BicycleSize entityModel = new BicycleSize();

    private static final String VALID_SIZE_NAME = "L";
    private static final String VALID_ID = "gasgasga";

    @InjectMocks
    BicycleSizeServiceImpl bicycleSizeService;

    @Mock
    BicycleSizeRepository bicycleSizeRepository;

    @Mock
    ModelMapper mapper;

    @Before
    public void init() {
        ModelMapper actualMapper = new ModelMapper();

        bicycleSizeServiceModel.setName(VALID_SIZE_NAME);
        entityModel.setName(VALID_SIZE_NAME);
        entityModel.setId(VALID_ID);


        when(mapper.map(any(BicycleSizeServiceModel.class), eq(BicycleSize.class)))
                .thenAnswer(invocationOnMock ->
                        actualMapper.map(invocationOnMock.getArguments()[0], BicycleSize.class));

        when(mapper.map(any(BicycleSize.class), eq(BicycleSizeServiceModel.class)))
                .thenAnswer(invocationOnMock ->
                        actualMapper.map(invocationOnMock.getArguments()[0], BicycleSizeServiceModel.class));

        when(bicycleSizeRepository.save(any())).thenAnswer(invocationOnMock -> invocationOnMock.getArguments()[0]);
    }

    @Test
    public void addSize_shouldSaveCorrectly_whenBicycleSizeDontPersistInDBAlready() {
        //Arrange
        when(bicycleSizeRepository.findByName(any()))
                .thenReturn(Optional.empty());

        //Act
        bicycleSizeService.addSize(bicycleSizeServiceModel);

        //Assert
        verify(bicycleSizeRepository).save(any());
    }

    @Test(expected = BicycleSizeAlreadyExistException.class)
    public void addSize_shouldThrowException_whenBicycleSizePersistInDBAlready() {
        //Arrange
        when(bicycleSizeRepository.findByName(any()))
                .thenReturn(Optional.of(entityModel));

        //Act
        bicycleSizeService.addSize(bicycleSizeServiceModel);
    }

    @Test
    public void findAllBicycleSizes_shouldReturnAllSizes() {
        //Arrange
        when(bicycleSizeRepository.findAll())
                .thenReturn(List.of(entityModel));

        //Act
        List<BicycleSizeServiceModel> categories = bicycleSizeService.findAllBicycleSizes();

        //Assert
        assertEquals(1, categories.size());
        assertEquals(VALID_SIZE_NAME, categories.get(0).getName());
    }

    @Test
    public void findAllBicycleSizes_shouldReturnEmptyCollection() {
        //Arrange
        when(bicycleSizeRepository.findAll())
                .thenReturn(new ArrayList<>());

        //Act
        List<BicycleSizeServiceModel> categories = bicycleSizeService.findAllBicycleSizes();

        //Assert
        assertEquals(0, categories.size());
    }

    @Test
    public void findById_shouldReturnSize_whenIdIsValid() {
        //Arrange
        entityModel.setId(VALID_ID);
        when(bicycleSizeRepository.findById(VALID_ID))
                .thenReturn(Optional.of(entityModel));

        //Act
        BicycleSizeServiceModel result = bicycleSizeService.findById(VALID_ID);

        //Assert
        assertEquals(VALID_ID, result.getId());
    }

    @Test(expected = BicycleSizeNotFoundException.class)
    public void findById_shouldThrowException_whenIdIsNotValid() {
        //Arrange
        when(bicycleSizeRepository.findById(VALID_ID))
                .thenReturn(Optional.empty());

        //Act
        bicycleSizeService.findById(VALID_ID);
    }

    @Test
    public void deleteBicycleSizeById_shouldDeleteCorrectly_whenDataIsValid() {
        //Arrange
        when(bicycleSizeRepository.findById(VALID_ID))
                .thenReturn(Optional.of(entityModel));

        //Act
        bicycleSizeService.deleteBicycleSizeById(VALID_ID);

        //Assert
        verify(bicycleSizeRepository).delete(any());
    }
}
