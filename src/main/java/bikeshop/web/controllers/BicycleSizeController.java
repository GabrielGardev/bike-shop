package bikeshop.web.controllers;

import bikeshop.domain.models.binding.BicycleSizeBindingModel;
import bikeshop.domain.models.service.BicycleSizeServiceModel;
import bikeshop.domain.models.view.BicycleSizeViewModel;
import bikeshop.service.BicycleSizeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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
    public ModelAndView addBicycleSize(){
        return view("bicycle/size/add-size");
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView addBicycleSizeConfirm(@ModelAttribute BicycleSizeBindingModel model){
        BicycleSizeServiceModel serviceModel = mapper.map(model, BicycleSizeServiceModel.class);
        bicycleSizeService.addSize(serviceModel);
        return redirect("/sizes/all");
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView allBicycleSizes(ModelAndView modelAndView){
        List<BicycleSizeViewModel> sizes = bicycleSizeService.findAllBicycleSizes()
                .stream()
                .map(c -> mapper.map(c, BicycleSizeViewModel.class))
                .collect(Collectors.toList());
        modelAndView.addObject("sizes", sizes);

        return view("bicycle/size/all-sizes", modelAndView);
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
