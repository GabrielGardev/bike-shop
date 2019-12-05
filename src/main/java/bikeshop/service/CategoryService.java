package bikeshop.service;

import bikeshop.domain.models.service.CategoryServiceModel;

import java.util.List;

public interface CategoryService {

    void addCategory(CategoryServiceModel categoryServiceModel);

    List<CategoryServiceModel> findAllCategories();

    CategoryServiceModel findById(String id);

    CategoryServiceModel editCategory(String id, CategoryServiceModel categoryServiceModel);

    void deleteCategory(String id);
}
