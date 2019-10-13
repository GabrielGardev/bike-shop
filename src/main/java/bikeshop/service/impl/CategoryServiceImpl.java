package bikeshop.service.impl;

import bikeshop.common.Constants;
import bikeshop.domain.entities.Category;
import bikeshop.domain.models.service.CategoryServiceModel;
import bikeshop.repository.CategoryRepository;
import bikeshop.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
        Category category = mapper.map(categoryServiceModel, Category.class);
        categoryRepository.saveAndFlush(category);
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
    public void editCategory(String id, CategoryServiceModel categoryServiceModel) {
        Category category = this.getCategory(id);
        category.setName(categoryServiceModel.getName());
        categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(String id) {
        Category category = this.getCategory(id);
        categoryRepository.delete(category);
    }

    private Category getCategory(String id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(Constants.INCORRECT_ID));
    }
}
