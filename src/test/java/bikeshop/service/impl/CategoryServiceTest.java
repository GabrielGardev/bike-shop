package bikeshop.service.impl;

import bikeshop.domain.entities.Category;
import bikeshop.domain.entities.Component;
import bikeshop.domain.models.service.CategoryServiceModel;
import bikeshop.domain.models.service.ComponentServiceModel;
import bikeshop.domain.models.service.UserServiceModel;
import bikeshop.error.CategoryAlreadyExistException;
import bikeshop.error.CategoryNotFoundException;
import bikeshop.repository.CategoryRepository;
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
public class CategoryServiceTest {

    private CategoryServiceModel categoryServiceModel = new CategoryServiceModel();
    private Category entityModel = new Category();

    private static final String VALID_CATEGORY_NAME = "MOUNTAIN";
    private static final String VALID_CATEGORY_NEW_NAME = "BMX";
    private static final String VALID_ID = "gasgasga";

    @InjectMocks
    CategoryServiceImpl categoryService;

    @Mock
    CategoryRepository categoryRepository;

    @Mock
    ModelMapper mapper;

    @Before
    public void init() {
        ModelMapper actualMapper = new ModelMapper();

        categoryServiceModel.setName(VALID_CATEGORY_NAME);
        entityModel.setName(VALID_CATEGORY_NAME);
        entityModel.setId(VALID_ID);


        when(mapper.map(any(CategoryServiceModel.class), eq(Category.class)))
                .thenAnswer(invocationOnMock ->
                        actualMapper.map(invocationOnMock.getArguments()[0], Category.class));

        when(mapper.map(any(Category.class), eq(CategoryServiceModel.class)))
                .thenAnswer(invocationOnMock ->
                        actualMapper.map(invocationOnMock.getArguments()[0], CategoryServiceModel.class));

        when(categoryRepository.save(any())).thenAnswer(invocationOnMock -> invocationOnMock.getArguments()[0]);
    }


    @Test
    public void addCategory_shouldSaveCorrectly_whenCategoryDontPersistInDBAlready() {
        //Arrange
        when(categoryRepository.findByName(any()))
                .thenReturn(Optional.empty());

        //Act
        categoryService.addCategory(categoryServiceModel);

        //Assert
        verify(categoryRepository).save(any());
    }

    @Test(expected = CategoryAlreadyExistException.class)
    public void addCategory_shouldThrowException_whenCategoryPersistInDBAlready() {
        //Arrange
        when(categoryRepository.findByName(any()))
                .thenReturn(Optional.of(entityModel));

        //Act
        categoryService.addCategory(categoryServiceModel);
    }

    @Test
    public void findAllCategories_shouldReturnAllCategories() {
        //Arrange
        when(categoryRepository.findAll())
                .thenReturn(List.of(entityModel));

        //Act
        List<CategoryServiceModel> categories = categoryService.findAllCategories();

        //Assert
        assertEquals(1, categories.size());
        assertEquals(VALID_CATEGORY_NAME, categories.get(0).getName());
    }

    @Test
    public void findAllCategories_shouldReturnEmptyCollection() {
        //Arrange
        when(categoryRepository.findAll())
                .thenReturn(new ArrayList<>());

        //Act
        List<CategoryServiceModel> categories = categoryService.findAllCategories();

        //Assert
        assertEquals(0, categories.size());
    }

    @Test
    public void findById_shouldReturnCategory_whenIdIsValid() {
        //Arrange
        entityModel.setId(VALID_ID);
        when(categoryRepository.findById(VALID_ID))
                .thenReturn(Optional.of(entityModel));

        //Act
        CategoryServiceModel categoryById = categoryService.findById(VALID_ID);

        //Assert
        assertEquals(VALID_ID, categoryById.getId());
    }

    @Test(expected = CategoryNotFoundException.class)
    public void findById_shouldThrowException_whenIdIsNotValid() {
        //Arrange
        when(categoryRepository.findById(VALID_ID))
                .thenReturn(Optional.empty());

        //Act
        CategoryServiceModel categoryById = categoryService.findById(VALID_ID);
    }

    @Test
    public void editCategory_shouldEditCorrectly_whenDateIsValid() {
        //Arrange
        when(categoryRepository.findByName(any()))
                .thenReturn(Optional.empty());
        when(categoryRepository.findById(VALID_ID))
                .thenReturn(Optional.of(entityModel));

        categoryServiceModel.setName(VALID_CATEGORY_NEW_NAME);

        //Act
        CategoryServiceModel result = categoryService.editCategory(VALID_ID, categoryServiceModel);

        //Assert
        verify(categoryRepository).save(any());
        assertEquals(VALID_CATEGORY_NEW_NAME, result.getName());
    }

    @Test(expected = CategoryAlreadyExistException.class)
    public void editCategory_shouldThrowException_whenNewNameAlreadyExist() {
        //Arrange
        when(categoryRepository.findByName(any()))
                .thenReturn(Optional.of(entityModel));

        //Act
        categoryService.editCategory(VALID_ID, categoryServiceModel);
    }

    @Test(expected = CategoryNotFoundException.class)
    public void editCategory_shouldThrowException_whenIdIsNotExit() {
        //Arrange
        when(categoryRepository.findByName(any()))
                .thenReturn(Optional.empty());

        when(categoryRepository.findById(VALID_ID))
                .thenReturn(Optional.empty());

        //Act
        categoryService.editCategory(VALID_ID, categoryServiceModel);
    }

    @Test
    public void deleteCategory_shouldDeleteCorrectly_whenDateIsValid() {
        //Arrange
        when(categoryRepository.findById(VALID_ID))
                .thenReturn(Optional.of(entityModel));

        //Act
        categoryService.deleteCategory(VALID_ID);

        //Assert
        verify(categoryRepository).delete(any());
    }
}