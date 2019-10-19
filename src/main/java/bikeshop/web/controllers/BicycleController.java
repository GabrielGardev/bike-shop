package bikeshop.web.controllers;

import bikeshop.domain.models.binding.BicycleAddBindingModel;
import bikeshop.domain.models.service.BicycleServiceModel;
import bikeshop.domain.models.service.ComponentServiceModel;
import bikeshop.service.BicycleService;
import bikeshop.service.CloudinaryService;
import bikeshop.service.ComponentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/bicycles")
public class BicycleController extends BaseController {

    private final BicycleService bicycleService;
    private final ModelMapper mapper;
    private final CloudinaryService cloudinaryService;
    private final ComponentService componentService;

    @Autowired
    public BicycleController(BicycleService bicycleService, ModelMapper mapper, CloudinaryService cloudinaryService, ComponentService componentService) {
        this.bicycleService = bicycleService;
        this.mapper = mapper;
        this.cloudinaryService = cloudinaryService;
        this.componentService = componentService;
    }

    @GetMapping("/add")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView addBicycle() {
        return view("bicycle/add-bicycle");
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView addBicycleConform(@ModelAttribute BicycleAddBindingModel model, RedirectAttributes redirectAttributes) throws IOException, IllegalAccessException {
        BicycleServiceModel serviceModel = mapper.map(model, BicycleServiceModel.class);
        serviceModel.setImageUrl(
                this.cloudinaryService.uploadImage(model.getImage())
        );
        serviceModel.setComponents(this.setComponents(model));
        bicycleService.addBicycle(serviceModel);

        return redirect("/home");
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
