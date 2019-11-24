package bikeshop.web.controllers;

import bikeshop.domain.models.binding.CartItemBindingModel;
import bikeshop.domain.models.service.BicycleServiceModel;
import bikeshop.domain.models.service.OrderItemServiceModel;
import bikeshop.domain.models.service.OrderServiceModel;
import bikeshop.domain.models.view.BicycleViewModel;
import bikeshop.domain.models.view.CartItemViewModel;
import bikeshop.service.BicycleService;
import bikeshop.service.OrderService;
import bikeshop.service.UserService;
import bikeshop.web.annotations.PageTitle;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController extends BaseController{

    private final BicycleService bicycleService;
    private final OrderService orderService;
    private final ModelMapper mapper;
    private final UserService userService;

    @Autowired
    public CartController(BicycleService bicycleService, OrderService orderService, ModelMapper mapper, UserService userService) {
        this.bicycleService = bicycleService;
        this.orderService = orderService;
        this.mapper = mapper;
        this.userService = userService;
    }

    @GetMapping("/details")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("Cart Details")
    public ModelAndView cartDetails(ModelAndView modelAndView,
                                    HttpSession session) {
        List<CartItemViewModel> cart = this.retrieveCart(session);
        modelAndView.addObject("totalPrice", this.calcTotal(cart));

        return super.view("cart/cart-details", modelAndView);
    }

    @PostMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView addToCartConfirm(@Valid @ModelAttribute(name = "cartModel") CartItemBindingModel cartModel,
                                         String id,
                                         HttpSession session,
                                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return view("bicycle/details");
        }
        BicycleViewModel bicycle = mapper.map(bicycleService.findById(id), BicycleViewModel.class);

        CartItemViewModel cartItem = mapper.map(cartModel, CartItemViewModel.class);
        cartItem.setBicycle(bicycle);

        List<CartItemViewModel> cart = this.retrieveCart(session);
        this.addItemToCart(cartItem, cart, cartModel.getBicycleSize());

        return redirect("/cart/details");
    }

    @DeleteMapping("/remove")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView removeFromCartConfirm(String id, HttpSession session) {
        this.removeItemFromCart(id, this.retrieveCart(session));

        return redirect("/cart/details");
    }

    @PostMapping("/checkout")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView checkoutConfirm(HttpSession session, Principal principal) {
        List<CartItemViewModel> cart = this.retrieveCart(session);

        OrderServiceModel orderServiceModel = this.prepareOrder(cart, principal.getName());
        orderService.createOrder(orderServiceModel);
        session.removeAttribute("shopping-cart");

        return redirect("/home");
    }

    @SuppressWarnings(value = "unchecked")
    private List<CartItemViewModel> retrieveCart(HttpSession session) {
        this.initCart(session);

        return (List<CartItemViewModel>) session.getAttribute("shopping-cart");
    }

    private void initCart(HttpSession session) {
        if (session.getAttribute("shopping-cart") == null) {
            session.setAttribute("shopping-cart", new ArrayList<>());
        }
    }

    private void addItemToCart(CartItemViewModel item, List<CartItemViewModel> cart, String bicycleSize) {
        for (CartItemViewModel shoppingCartItem : cart) {
            if (shoppingCartItem.getBicycle().getId().equals(item.getBicycle().getId())
            && shoppingCartItem.getBicycleSize().equals(bicycleSize)) {
                shoppingCartItem.setQuantity(shoppingCartItem.getQuantity() + item.getQuantity());
                return;
            }
        }
        cart.add(item);
    }

    private BigDecimal calcTotal(List<CartItemViewModel> cart) {
        BigDecimal result = new BigDecimal(0);
        for (CartItemViewModel item : cart) {
            BigDecimal price = this.getIndividualPriceWithDiscount(item);

            result = result.add(price.multiply(new BigDecimal(item.getQuantity())));
        }

        return result;
    }

    private BigDecimal getIndividualPriceWithDiscount(CartItemViewModel item) {
        BigDecimal price = item.getBicycle().getPrice();
        double discount = (100 - item.getBicycle().getDiscount()) / 100;

        price = price.multiply(BigDecimal.valueOf(discount));
        return price;
    }

    private void removeItemFromCart(String id, List<CartItemViewModel> cart) {
        cart.removeIf(ci -> ci.getBicycle().getId().equals(id));
    }

    private OrderServiceModel prepareOrder(List<CartItemViewModel> cart, String username) {
        OrderServiceModel orderServiceModel = new OrderServiceModel();
        orderServiceModel.setUser(userService.findUserByUsername(username));

        List<OrderItemServiceModel> bicycles = new ArrayList<>();
        for (CartItemViewModel item : cart) {
            OrderItemServiceModel orderItemServiceModel = mapper.map(item, OrderItemServiceModel.class);
            orderItemServiceModel.setPrice(this.getIndividualPriceWithDiscount(item));

            bicycles.add(orderItemServiceModel);
        }

        orderServiceModel.setBicycles(bicycles);
        orderServiceModel.setTotalPrice(this.calcTotal(cart));

        return orderServiceModel;
    }
}
