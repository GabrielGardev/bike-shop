package bikeshop.web.controllers;

import bikeshop.domain.models.service.OrderServiceModel;
import bikeshop.domain.models.view.BicycleViewModel;
import bikeshop.domain.models.view.OrderViewModel;
import bikeshop.service.OrderService;
import bikeshop.web.annotations.PageTitle;
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

import static bikeshop.common.Constants.DATE_PATTERN;

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

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PageTitle("All Orders")
    public ModelAndView getAllOrders(ModelAndView modelAndView){
        List<OrderServiceModel> serviceModels = orderService.findAllOrders();
        List<OrderViewModel> viewModels = this.getOrderViewModels(serviceModels);

        modelAndView.addObject("orders", viewModels);
        return view("order/list-orders", modelAndView);
    }

    @GetMapping("/details/{id}")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("Orders Details")
    public ModelAndView allOrderDetails(@PathVariable String id, ModelAndView modelAndView) {
        OrderServiceModel order = orderService.findOrderById(id);
        OrderViewModel orderViewModel = mapper.map(order, OrderViewModel.class);
        List<BicycleViewModel> bicycles = orderViewModel.getBicycles();

        modelAndView.addObject("bicycles", bicycles);

        return view("order/order-details", modelAndView);
    }

    @GetMapping("/customer")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("My Orders")
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
                        viewModel.setDate(o.getFinishedOn().format(DateTimeFormatter.ofPattern(DATE_PATTERN)));
                        return viewModel;
                    })
                    .collect(Collectors.toList());
    }
}
