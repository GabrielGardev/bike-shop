package bikeshop.service;

import bikeshop.domain.models.service.CategoryServiceModel;

import java.util.List;

public interface CategoryService {

    void addCategory(CategoryServiceModel categoryServiceModel);

    List<CategoryServiceModel> findAllCategories();

    CategoryServiceModel findById(String id);

    void editCategory(String id, CategoryServiceModel categoryServiceModel);

    void deleteCategory(String id);
}
