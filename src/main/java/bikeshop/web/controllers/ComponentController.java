package bikeshop.web.controllers;

import bikeshop.domain.models.binding.ComponentEditBindingModel;
import bikeshop.service.ComponentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/components")
public class ComponentController extends BaseController{

    private final ComponentService componentService;

    @Autowired
    public ComponentController(ComponentService componentService) {
        this.componentService = componentService;
    }

    @PatchMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView editComponent(@PathVariable String id,
                                      @Valid @ModelAttribute(name = "componentsModel") ComponentEditBindingModel componentsModel,
                                      @RequestParam(value = "bicycleId") String bicycleId,
                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return view("bicycle/edit-bicycle");
        }

        componentService.editById(id, componentsModel.getDescription());
        return redirect("/bicycles/edit/" + bicycleId);
    }
}
