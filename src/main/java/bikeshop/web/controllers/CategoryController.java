package bikeshop.web.controllers;

import bikeshop.domain.models.binding.CategoryBindingModel;
import bikeshop.domain.models.service.CategoryServiceModel;
import bikeshop.domain.models.view.CategoryViewModel;
import bikeshop.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/categories")
public class CategoryController extends BaseController {

    private final CategoryService categoryService;
    private final ModelMapper mapper;

    @Autowired
    public CategoryController(CategoryService categoryService, ModelMapper mapper) {
        this.categoryService = categoryService;
        this.mapper = mapper;
    }

    @GetMapping("/add")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView addCategory(){
        return view("category/add-category");
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView addCategoryConfirm(@ModelAttribute CategoryBindingModel model){
        CategoryServiceModel serviceModel = mapper.map(model, CategoryServiceModel.class);
        categoryService.addCategory(serviceModel);
        return redirect("/categories/all");
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView allCategories(ModelAndView modelAndView){
        List<CategoryViewModel> categories = categoryService.findAllCategories()
                .stream()
                .map(c -> mapper.map(c, CategoryViewModel.class))
                .collect(Collectors.toList());
        modelAndView.addObject("categories", categories);

        return view("category/all-categories", modelAndView);
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView editCategory(@PathVariable String id, ModelAndView modelAndView){
        CategoryServiceModel categoryServiceById = categoryService.findById(id);
        CategoryViewModel categoryViewModel = mapper.map(categoryServiceById, CategoryViewModel.class);

        modelAndView.addObject("model", categoryViewModel);
        return view("category/edit-category", modelAndView);
    }

    @PostMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView editCategoryConfirm(@PathVariable String id, @ModelAttribute CategoryBindingModel model){
        CategoryServiceModel serviceModel = mapper.map(model, CategoryServiceModel.class);
        categoryService.editCategory(id, serviceModel);

        return redirect("/categories/all");
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView deleteCategory(@PathVariable String id, ModelAndView modelAndView){
        CategoryServiceModel categoryServiceById = categoryService.findById(id);
        CategoryViewModel categoryViewModel = mapper.map(categoryServiceById, CategoryViewModel.class);

        modelAndView.addObject("model", categoryViewModel);
        return view("category/delete-category", modelAndView);
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView deleteCategoryConfirm(@PathVariable String id) {
        categoryService.deleteCategory(id);

        return redirect("/categories/all");
    }

    @GetMapping("/fetch")
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    public List<CategoryViewModel> fetchCategories() {
        return categoryService.findAllCategories()
                .stream()
                .map(c -> mapper.map(c, CategoryViewModel.class))
                .collect(Collectors.toList());
    }
}
