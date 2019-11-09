package bikeshop.web.controllers;

import bikeshop.domain.models.binding.BicycleAddBindingModel;
import bikeshop.domain.models.binding.BicycleEditBindingModel;
import bikeshop.domain.models.service.BicycleServiceModel;
import bikeshop.domain.models.service.ComponentServiceModel;
import bikeshop.domain.models.view.BicycleByCategoryViewModel;
import bikeshop.domain.models.view.BicycleViewModel;
import bikeshop.domain.models.view.ComponentViewModel;
import bikeshop.error.BicycleNotFoundException;
import bikeshop.service.BicycleService;
import bikeshop.service.CloudinaryService;
import bikeshop.service.ComponentService;
import bikeshop.web.annotations.PageTitle;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/bicycles")
public class BicycleController extends BaseController {

    private final BicycleService bicycleService;
    private final ModelMapper mapper;
    private final CloudinaryService cloudinaryService;
    private final ComponentService componentService;

    @Autowired
    public BicycleController(BicycleService bicycleService,
                             ModelMapper mapper,
                             CloudinaryService cloudinaryService,
                             ComponentService componentService) {
        this.bicycleService = bicycleService;
        this.mapper = mapper;
        this.cloudinaryService = cloudinaryService;
        this.componentService = componentService;
    }

    @GetMapping("/add")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @PageTitle("Add Bicycle")
    public ModelAndView addBicycle() {
        return view("bicycle/add-bicycle");
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView addBicycleConform(@ModelAttribute BicycleAddBindingModel model) throws IOException, IllegalAccessException {
        BicycleServiceModel serviceModel = mapper.map(model, BicycleServiceModel.class);
        serviceModel.setImageUrl(
                this.cloudinaryService.uploadImage(model.getImage())
        );
        serviceModel.setComponents(this.setComponents(model));
        bicycleService.addBicycle(serviceModel);

        return redirect("/bicycles/all");
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @PageTitle("All Bicycle")
    public ModelAndView allBicycles(ModelAndView modelAndView) {
        List<BicycleViewModel> bicycles = bicycleService.findAll()
                .stream()
                .map(bike -> mapper.map(bike, BicycleViewModel.class))
                .collect(Collectors.toList());
        modelAndView.addObject("bicycles", bicycles);

        return view("bicycle/all-bicycles", modelAndView);
    }

    @GetMapping("/details/{id}")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("Bicycle Details")
    public ModelAndView bicycleDetails(@PathVariable String id,
                                       ModelAndView modelAndView){
        BicycleServiceModel serviceModel = bicycleService.findById(id);
        BicycleViewModel bicycle = mapper.map(serviceModel, BicycleViewModel.class);
        modelAndView.addObject("bicycle", bicycle);

        return view("bicycle/details", modelAndView);
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @PageTitle("Edit Bicycle")
    public ModelAndView edit(@PathVariable String id, ModelAndView modelAndView){
        BicycleServiceModel serviceModel = bicycleService.findById(id);
        BicycleViewModel bicycle = mapper.map(serviceModel, BicycleViewModel.class);
        modelAndView.addObject("bicycle", bicycle);

        return view("bicycle/edit-bicycle", modelAndView);
    }

    @PatchMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView editConfirm(@PathVariable String id,
                                    @ModelAttribute BicycleEditBindingModel model){
        BicycleServiceModel serviceModel = mapper.map(model, BicycleServiceModel.class);
        bicycleService.editById(id, serviceModel);
        return redirect("/bicycles/all");
    }

    @PatchMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView delete(@PathVariable String id){
        bicycleService.deleteBicycleById(id);
        return redirect("/bicycles/all");
    }

    @GetMapping("/{name}")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("Bicycle {name}")
    public ModelAndView getBicyclesByCategoryId(@PathVariable String name, ModelAndView modelAndView) {
        List<BicycleByCategoryViewModel> allByCategoryId = bicycleService.findAllByCategory(name)
                .stream()
                .map(bike -> mapper.map(bike, BicycleByCategoryViewModel.class))
                .collect(Collectors.toList());

        modelAndView.addObject("bicycles", allByCategoryId);

        return view("bicycle/bicycles-by-category", modelAndView);
    }

    private Set<ComponentServiceModel> setComponents(BicycleAddBindingModel model) throws IllegalAccessException {
        Set<ComponentServiceModel> components = new HashSet<>();
        for (Field field : model.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            ComponentServiceModel componentServiceModel = new ComponentServiceModel();

            if (field.getName().equals("fork") || field.getName().equals("frame")
                    || field.getName().equals("breaks") || field.getName().equals("tyres")
                    || field.getName().equals("seat")) {
                componentServiceModel.setType(field.getName());
                componentServiceModel.setDescription((String) field.get(model));
                components.add(componentService.saveComponent(componentServiceModel));
            }
        }
        return components;
    }
}
