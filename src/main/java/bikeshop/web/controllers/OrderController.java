package bikeshop.web.controllers;

import bikeshop.domain.models.binding.OrderCreateBindingModel;
import bikeshop.domain.models.service.OrderServiceModel;
import bikeshop.domain.models.view.OrderViewModel;
import bikeshop.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/orders")
public class OrderController extends BaseController {

    private final OrderService orderService;
    private final ModelMapper mapper;

    @Autowired
    public OrderController(OrderService orderService, ModelMapper mapper) {
        this.orderService = orderService;
        this.mapper = mapper;
    }

    @PostMapping("/order-details/{id}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView orderBicycle(@PathVariable(name = "id") String bicycleId,
                                     @ModelAttribute OrderCreateBindingModel model,
                                     ModelAndView modelAndView,
                                     Principal principal){

        OrderServiceModel serviceModel = mapper.map(model, OrderServiceModel.class);
        OrderServiceModel order = orderService.viewOrder(bicycleId, principal.getName(), serviceModel);
        OrderViewModel orderViewModel = mapper.map(order, OrderViewModel.class);

        modelAndView.addObject("order", orderViewModel);
        return view("order/order-details", modelAndView);
    }

    @PostMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView orderBicycleConfirm(@RequestParam String bicycleId,
                                            @RequestParam String bicycleSize,
                                            @RequestParam String quantity,
                                            @RequestParam String totalPrice,
                                            Principal principal){
        orderService.createOrder(bicycleId, bicycleSize, quantity, totalPrice, principal.getName());

        return view("order/user-orders");
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView getAllOrders(ModelAndView modelAndView){
        List<OrderServiceModel> serviceModels = orderService.findAllOrders();
        List<OrderViewModel> viewModels = this.getOrderViewModels(serviceModels);

        modelAndView.addObject("orders", viewModels);
        return view("order/list-orders", modelAndView);
    }

    @GetMapping("/customer")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView getCustomerOrders(ModelAndView modelAndView, Principal principal){
        List<OrderServiceModel> serviceModels = orderService.findByCustomerName(principal.getName());
        List<OrderViewModel> viewModels = this.getOrderViewModels(serviceModels);

        modelAndView.addObject("orders", viewModels);
        return view("order/list-orders", modelAndView);
    }

    private List<OrderViewModel> getOrderViewModels(List<OrderServiceModel> serviceModels) {
        return serviceModels.stream()
                    .map(o -> {
                        OrderViewModel viewModel = mapper.map(o, OrderViewModel.class);
                        viewModel.setDate(o.getFinishedOn().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                        return viewModel;
                    })
                    .collect(Collectors.toList());
    }
}
