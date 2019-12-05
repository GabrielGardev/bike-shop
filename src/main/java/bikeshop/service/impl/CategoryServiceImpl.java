package bikeshop.service.impl;

import bikeshop.domain.entities.Category;
import bikeshop.domain.models.service.CategoryServiceModel;
import bikeshop.error.CategoryAlreadyExistException;
import bikeshop.error.CategoryNotFoundException;
import bikeshop.repository.CategoryRepository;
import bikeshop.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static bikeshop.common.Constants.DUPLICATE_CATEGORY;
import static bikeshop.common.Constants.INCORRECT_ID;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper mapper;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper mapper) {
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
    }

    @Override
    public void addCategory(CategoryServiceModel categoryServiceModel) {
        this.checkIfCategoryAlreadyExist(categoryServiceModel.getName());

        Category category = mapper.map(categoryServiceModel, Category.class);
        categoryRepository.save(category);
    }

    @Override
    public List<CategoryServiceModel> findAllCategories() {
       return categoryRepository.findAll()
                .stream()
                .map(c -> mapper.map(c, CategoryServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public CategoryServiceModel findById(String id) {
        Category category = this.getCategory(id);
        return mapper.map(category, CategoryServiceModel.class);
    }

    @Override
    public CategoryServiceModel editCategory(String id, CategoryServiceModel categoryServiceModel) {
        String name = categoryServiceModel.getName();
        this.checkIfCategoryAlreadyExist(name);

        Category category = this.getCategory(id);
        category.setName(name);
        Category saved = categoryRepository.save(category);
        return mapper.map(saved, CategoryServiceModel.class);
    }

    @Override
    public void deleteCategory(String id) {
        Category category = this.getCategory(id);
        categoryRepository.delete(category);
    }

    private Category getCategory(String id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(INCORRECT_ID));
    }


    private void checkIfCategoryAlreadyExist(String name) {
        Category category = categoryRepository.findByName(name).orElse(null);

        if (category != null){
            throw new CategoryAlreadyExistException(DUPLICATE_CATEGORY);
        }
    }
}
