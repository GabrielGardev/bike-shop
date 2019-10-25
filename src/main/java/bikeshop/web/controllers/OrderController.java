package bikeshop.web.controllers;

import bikeshop.domain.models.binding.OrderCreateBindingModel;
import bikeshop.domain.models.service.BicycleServiceModel;
import bikeshop.domain.models.service.UserServiceModel;
import bikeshop.domain.models.view.BicycleViewModel;
import bikeshop.domain.models.view.OrderViewModel;
import bikeshop.service.BicycleService;
import bikeshop.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
@RequestMapping("/orders")
public class OrderController extends BaseController {

    private final BicycleService bicycleService;
    private final UserService userService;
    private final ModelMapper mapper;

    @Autowired
    public OrderController(BicycleService bicycleService, UserService userService, ModelMapper mapper) {
        this.bicycleService = bicycleService;
        this.userService = userService;
        this.mapper = mapper;
    }

    @PostMapping("/order-details/{id}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView orderBicycle(@PathVariable String id,
                                     @ModelAttribute OrderCreateBindingModel model,
                                     ModelAndView modelAndView,
                                     Principal principal){
        BicycleServiceModel serviceModel = bicycleService.findById(id);

        BicycleViewModel bicycleViewModel = mapper.map(serviceModel, BicycleViewModel.class);
        OrderViewModel orderViewModel = mapper.map(model, OrderViewModel.class);
        this.setUserToViewModel(principal.getName(), orderViewModel);

        modelAndView.addObject("bicycle", bicycleViewModel);
        modelAndView.addObject("order", orderViewModel);
        return view("order/order-details", modelAndView);
    }

    private void setUserToViewModel(String name, OrderViewModel orderViewModel) {
        UserServiceModel user = userService.findUserByUsername(name);
        orderViewModel.setFirstName(user.getFirstName());
        orderViewModel.setLastName(user.getLastName());
        orderViewModel.setEmail(user.getEmail());
    }
}
