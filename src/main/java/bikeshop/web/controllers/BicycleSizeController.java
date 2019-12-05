package bikeshop.web.controllers;

import bikeshop.domain.models.binding.BicycleSizeBindingModel;
import bikeshop.domain.models.service.BicycleSizeServiceModel;
import bikeshop.domain.models.view.BicycleSizeViewModel;
import bikeshop.service.BicycleSizeService;
import bikeshop.web.annotations.PageTitle;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/sizes")
public class BicycleSizeController extends BaseController{

    private final BicycleSizeService bicycleSizeService;
    private final ModelMapper mapper;

    @Autowired
    public BicycleSizeController(BicycleSizeService bicycleSizeService, ModelMapper mapper) {
        this.bicycleSizeService = bicycleSizeService;
        this.mapper = mapper;
    }

    @GetMapping("/add")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @PageTitle("Add Bicycle Size")
    public ModelAndView addBicycleSize(@ModelAttribute(name = "model") BicycleSizeBindingModel model){
        return view("bicycle/size/add-size");
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView addBicycleSizeConfirm(@Valid @ModelAttribute(name = "model") BicycleSizeBindingModel model,
                                              BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return view("bicycle/size/add-size");
        }

        BicycleSizeServiceModel serviceModel = mapper.map(model, BicycleSizeServiceModel.class);
        bicycleSizeService.addSize(serviceModel);
        return redirect("/sizes/all");
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @PageTitle("All Bicycle Size")
    public ModelAndView allBicycleSizes(ModelAndView modelAndView){
        List<BicycleSizeViewModel> sizes = bicycleSizeService.findAllBicycleSizes()
                .stream()
                .map(c -> mapper.map(c, BicycleSizeViewModel.class))
                .collect(Collectors.toList());
        modelAndView.addObject("sizes", sizes);

        return view("bicycle/size/all-sizes", modelAndView);
    }

    @PatchMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView delete(@PathVariable String id){
        bicycleSizeService.deleteBicycleSizeById(id);
        return redirect("/sizes/all");
    }

    @GetMapping("/fetch")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @ResponseBody
    public List<BicycleSizeViewModel> fetchBicycleSizes() {
        return bicycleSizeService.findAllBicycleSizes()
                .stream()
                .map(c -> mapper.map(c, BicycleSizeViewModel.class))
                .collect(Collectors.toList());
    }
}
