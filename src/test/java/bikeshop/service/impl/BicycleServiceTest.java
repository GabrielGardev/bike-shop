package bikeshop.service.impl;

import bikeshop.domain.entities.Bicycle;
import bikeshop.domain.entities.BicycleSize;
import bikeshop.domain.entities.Category;
import bikeshop.domain.models.service.BicycleServiceModel;
import bikeshop.domain.models.service.BicycleSizeServiceModel;
import bikeshop.domain.models.service.CategoryServiceModel;
import bikeshop.domain.models.service.ComponentServiceModel;
import bikeshop.error.BicycleAlreadyExistException;
import bikeshop.error.BicycleNotFoundException;
import bikeshop.error.CategoryNotFoundException;
import bikeshop.repository.BicycleRepository;
import bikeshop.repository.BicycleSizeRepository;
import bikeshop.repository.CategoryRepository;
import bikeshop.service.CategoryService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BicycleServiceTest {

    private BicycleServiceModel bicycleServiceModel = new BicycleServiceModel();
    private Bicycle entityModel = new Bicycle();
    private CategoryServiceModel categoryServiceModel = new CategoryServiceModel();
    private BicycleSize bicycleSize = new BicycleSize();
    private Category category = new Category();
    private List<BicycleSize> sizes = new ArrayList<>();

    private static final String RANDOM_ID = "gasgaga";
    private static final String CATEGORY_ID = "category-random-id";
    private static final String CATEGORY_NAME = "MOUNTAIN";
    private static final String COLOR = "Black";
    private static final String BICYCLE_DESCRIPTION = "it's a mega ultra cool bike";
    private static final String MAKE = "Oryx";
    private static final String NEW_MAKE = "Ram";
    private static final String MODEL = "c-18";
    private static final String SIZE_NAME = "L";
    private static final String COMPONENT_TYPE = "Seat";
    private static final String COMPONENT_DESCRIPTION = "Large";
    private static final String IMG_URL = "www.image.con";
    private static final BigDecimal BICYCLE_PRICE = BigDecimal.valueOf(900);

    @InjectMocks
    BicycleServiceImpl bicycleService;

    @Mock
    BicycleRepository bicycleRepository;

    @Mock
    CategoryRepository categoryRepository;

    @Mock
    CategoryService categoryService;

    @Mock
    BicycleSizeRepository bicycleSizeRepository;

    @Mock
    ModelMapper mapper;

    @Before
    public void init(){
        ModelMapper actualMapper = new ModelMapper();

        when(mapper.map(any(BicycleServiceModel.class), eq(Bicycle.class)))
                .thenAnswer(invocationOnMock ->
                        actualMapper.map(invocationOnMock.getArguments()[0], Bicycle.class));

        when(mapper.map(any(Bicycle.class), eq(BicycleServiceModel.class)))
                .thenAnswer(invocationOnMock ->
                        actualMapper.map(invocationOnMock.getArguments()[0], BicycleServiceModel.class));

        when(mapper.map(any(CategoryServiceModel.class), eq(Category.class)))
                .thenAnswer(invocationOnMock ->
                        actualMapper.map(invocationOnMock.getArguments()[0], Category.class));

        categoryServiceModel.setId(CATEGORY_ID);
        categoryServiceModel.setName(CATEGORY_NAME);

        bicycleSize.setId(RANDOM_ID);
        bicycleSize.setName(SIZE_NAME);
        sizes.add(bicycleSize);

        bicycleServiceModel.setId(RANDOM_ID);
        bicycleServiceModel.setCategory(CATEGORY_ID);

        Set<String> bSize = new HashSet<>();
        bSize.add(SIZE_NAME);
        bicycleServiceModel.setBicycleSize(bSize);

        Set<ComponentServiceModel> components = new HashSet<>();
        ComponentServiceModel componentServiceModel = new ComponentServiceModel();
        componentServiceModel.setType(COMPONENT_TYPE);
        componentServiceModel.setDescription(COMPONENT_DESCRIPTION);
        components.add(componentServiceModel);
        bicycleServiceModel.setComponents(components);

        bicycleServiceModel.setImageUrl(IMG_URL);
        bicycleServiceModel.setColor(COLOR);
        bicycleServiceModel.setDescription(BICYCLE_DESCRIPTION);
        bicycleServiceModel.setDiscount(0d);
        bicycleServiceModel.setMake(MAKE);
        bicycleServiceModel.setModel(MODEL);
        bicycleServiceModel.setPrice(BICYCLE_PRICE);

        category.setName(CATEGORY_NAME);
        category.setId(RANDOM_ID);

        entityModel.setCategory(category);
        entityModel.setBicycleSize(Set.of(bicycleSize));
        entityModel.setId(RANDOM_ID);
        entityModel.setMake(MAKE);
        entityModel.setDiscount(0d);
        entityModel.setModel(MODEL);
        entityModel.setDescription(BICYCLE_DESCRIPTION);
        entityModel.setImageUrl(IMG_URL);
        entityModel.setColor(COLOR);
        entityModel.setPrice(BICYCLE_PRICE);
    }

    @Test
    public void addBicycle_shouldSaveCorrectly_whenBicycleDontPersistInDB(){
        //Arrange
        when(bicycleRepository.findByMakeAndModelAndColor(MAKE, MODEL, COLOR))
                .thenReturn(Optional.empty());

        when(categoryService.findById(any()))
                .thenReturn(categoryServiceModel);

        when(bicycleSizeRepository.findAllById(any()))
                .thenReturn(sizes);

        //Act
        bicycleService.addBicycle(bicycleServiceModel);

        //Assert
        verify(bicycleRepository).save(any());
    }

    @Test(expected = BicycleAlreadyExistException.class)
    public void addBicycle_shouldThrowException_whenBicyclePersistInDB(){
        //Arrange
        when(bicycleRepository.findByMakeAndModelAndColor(MAKE, MODEL, COLOR))
                .thenReturn(Optional.of(new Bicycle()));

        //Act
        bicycleService.addBicycle(bicycleServiceModel);
    }

    @Test(expected = CategoryNotFoundException.class)
    public void addBicycle_shouldThrowException_whenCategoryDontExist(){
        //Arrange
        when(bicycleRepository.findByMakeAndModelAndColor(MAKE, MODEL, COLOR))
                .thenReturn(Optional.empty());

        when(categoryService.findById(any()))
                .thenThrow(CategoryNotFoundException.class);

        //Act
        bicycleService.addBicycle(bicycleServiceModel);
    }

    @Test
    public void findAll_shouldReturnAllBicycles(){
        //Arrange
        when(bicycleRepository.findAll())
                .thenReturn(List.of(entityModel));

        //Act
        List<BicycleServiceModel> result = bicycleService.findAll();

        //Assert
        assertEquals(1, result.size());
        assertEquals(MAKE, result.get(0).getMake());
    }

    @Test
    public void findAll_shouldReturnEmptyCollection() {
        //Arrange
        when(bicycleRepository.findAll())
                .thenReturn(new ArrayList<>());

        //Act
        List<BicycleServiceModel> result = bicycleService.findAll();

        //Assert
        assertEquals(0, result.size());
    }

    @Test
    public void findById_shouldReturnCorrect_whenIdIsValid(){
        //Arrange
        when(bicycleRepository.findById(any()))
                .thenReturn(Optional.of(entityModel));

        //Act
        BicycleServiceModel result = bicycleService.findById(RANDOM_ID);

        //Assert
        assertEquals(RANDOM_ID, result.getId());
        assertEquals(MAKE, result.getMake());
    }

    @Test(expected = BicycleNotFoundException.class)
    public void findById_shouldThrowException_whenIdIsNotValid(){
        //Arrange
        when(bicycleRepository.findById(any()))
                .thenReturn(Optional.empty());

        //Act
        bicycleService.findById(RANDOM_ID);
    }

    @Test
    public void editById_shouldEditBicycleCorrectly(){
        //Arrange
        when(bicycleRepository.findById(any()))
                .thenReturn(Optional.of(entityModel));

        when(categoryRepository.findByName(any()))
                .thenReturn(Optional.of(category));

        when(bicycleSizeRepository.findAll())
                .thenReturn(sizes);

        //Act
        bicycleService.editById(RANDOM_ID, bicycleServiceModel);

        //Assert
        verify(bicycleRepository).save(any());
    }

    @Test(expected = BicycleNotFoundException.class)
    public void editById_shouldThrowException_WhenIdIsNotValid(){
        //Arrange
        when(bicycleRepository.findById(any()))
                .thenReturn(Optional.empty());

        //Act
        bicycleService.editById(RANDOM_ID, bicycleServiceModel);
    }

    @Test(expected = BicycleAlreadyExistException.class)
    public void editById_shouldThrowException_WhenThereIsAnotherBicycleWithTheSameParameters(){
        entityModel.setMake(NEW_MAKE);

        //Arrange
        when(bicycleRepository.findById(any()))
                .thenReturn(Optional.of(entityModel));

        when(bicycleRepository.findByMakeAndModelAndColor(any(), any(), any()))
                .thenReturn(Optional.of(entityModel));

        //Act
        bicycleService.editById(RANDOM_ID, bicycleServiceModel);
    }

    @Test(expected = CategoryNotFoundException.class)
    public void editById_shouldThrowException_WhenCategoryDontExist(){
        //Arrange
        when(bicycleRepository.findById(any()))
                .thenReturn(Optional.of(entityModel));

        when(categoryRepository.findByName(any()))
                .thenReturn(Optional.empty());

        //Act
        bicycleService.editById(RANDOM_ID, bicycleServiceModel);
    }

    @Test
    public void deleteBicycleById_shouldDeleteCorrectly_whenDataIsValid() {
        //Arrange
        when(bicycleRepository.findById(any()))
                .thenReturn(Optional.of(entityModel));

        //Act
        bicycleService.deleteBicycleById(RANDOM_ID);

        //Assert
        verify(bicycleRepository).delete(any());
    }

    @Test
    public void findAllByCategory_shouldReturnCorrectly_whenDataIsValid() {
        //Arrange
        when(bicycleRepository.findAllByCategoryName(any()))
                .thenReturn(List.of(entityModel));

        //Act
        List<BicycleServiceModel> result = bicycleService.findAllByCategory(CATEGORY_NAME);

        //Assert
        assertEquals(1, result.size());
    }

    @Test
    public void findAllByCategory_shouldReturnEmptyCollection_whenNameIsNotValid() {
        //Arrange
        when(bicycleRepository.findAllByCategoryName(any()))
                .thenReturn(new ArrayList<>());

        //Act
        List<BicycleServiceModel> result = bicycleService.findAllByCategory(CATEGORY_NAME);

        //Assert
        assertEquals(0, result.size());
    }

    @Test
    public void findAllOnPromo_shouldReturnCorrectly_whenDataIsValid() {
        //Arrange
        when(bicycleRepository.findAllByDiscountIsGreaterThan(any()))
                .thenReturn(List.of(entityModel));

        //Act
        List<BicycleServiceModel> result = bicycleService.findAllOnPromo();

        //Assert
        assertEquals(1, result.size());
    }

    @Test
    public void findAllOnPromo_shouldReturnEmptyCollection_whenNoBicyclesOnPromo() {
        //Arrange
        when(bicycleRepository.findAllByDiscountIsGreaterThan(any()))
                .thenReturn(new ArrayList<>());

        //Act
        List<BicycleServiceModel> result = bicycleService.findAllOnPromo();

        //Assert
        assertEquals(0, result.size());
    }
}
