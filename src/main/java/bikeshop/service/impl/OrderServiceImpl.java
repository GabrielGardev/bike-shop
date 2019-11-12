package bikeshop.service.impl;

import bikeshop.domain.entities.Bicycle;
import bikeshop.domain.entities.BicycleSize;
import bikeshop.domain.entities.Order;
import bikeshop.domain.entities.User;
import bikeshop.domain.models.service.BicycleServiceModel;
import bikeshop.domain.models.service.OrderServiceModel;
import bikeshop.domain.models.service.UserServiceModel;
import bikeshop.error.BicycleNotFoundException;
import bikeshop.error.BicycleSizeNotFoundException;
import bikeshop.repository.BicycleRepository;
import bikeshop.repository.BicycleSizeRepository;
import bikeshop.repository.OrderRepository;
import bikeshop.repository.UserRepository;
import bikeshop.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static bikeshop.common.Constants.*;

@Service
public class OrderServiceImpl implements OrderService {

    private final BicycleServiceImpl bicycleService;
    private final BicycleRepository bicycleRepository;
    private final UserRepository userRepository;
    private final UserServiceImpl userService;
    private final OrderRepository orderRepository;
    private final BicycleSizeRepository sizeRepository;
    private final ModelMapper mapper;

    @Autowired
    public OrderServiceImpl(BicycleServiceImpl bicycleService, BicycleRepository bicycleRepository,
                            UserRepository userRepository, UserServiceImpl userService,
                            OrderRepository orderRepository, BicycleSizeRepository sizeRepository,
                            ModelMapper mapper) {
        this.bicycleService = bicycleService;
        this.bicycleRepository = bicycleRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.orderRepository = orderRepository;
        this.sizeRepository = sizeRepository;
        this.mapper = mapper;
    }

    @Override
    public OrderServiceModel viewOrder(String bicycleId, String username,
                                       OrderServiceModel order){
        BicycleServiceModel bicycle = bicycleService.findById(bicycleId);
        UserServiceModel user = userService.findUserByUsername(username);

        order.setBicycle(bicycle);
        order.setUser(user);

        BigDecimal price = bicycle.getPrice();
        if (bicycle.getDiscount() != null){
            double discount = (100 - bicycle.getDiscount()) / 100;
            price = price.multiply(BigDecimal.valueOf(discount));
        }
        BigDecimal totalPrice = price
                .multiply(BigDecimal.valueOf(order.getQuantity()));
        order.setTotalPrice(totalPrice);

        return order;
    }

    @Override
    public void createOrder(String bicycleId, String bicycleSizeName,
                            String quantity, String totalPrice, String username) {
        Bicycle bicycle = bicycleRepository.findById(bicycleId)
                .orElseThrow(() -> new BicycleNotFoundException(INCORRECT_ID));

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(USERNAME_NOT_FOUND));

        BicycleSize bicycleSize = sizeRepository.findByName(bicycleSizeName)
                .orElseThrow(() -> new BicycleSizeNotFoundException(INCORRECT_SIZE_NAME));

        Order order = new Order();
        order.setBicycle(bicycle);
        order.setUser(user);
        order.setBicycleSize(bicycleSize);
        order.setQuantity(Integer.valueOf(quantity));
        order.setTotalPrice(BigDecimal.valueOf(Double.parseDouble(totalPrice)));
        order.setFinishedOn(LocalDateTime.now());
        orderRepository.save(order);
    }

    @Override
    public List<OrderServiceModel> findAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(o -> {
                    OrderServiceModel model = mapper.map(o, OrderServiceModel.class);
                    model.setBicycleSize(o.getBicycleSize().getName());
                    return model;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderServiceModel> findByCustomerName(String username) {
        return orderRepository.findAllByUserUsername(username)
                .stream()
                .map(o -> {
                    OrderServiceModel model = mapper.map(o, OrderServiceModel.class);
                    model.setBicycleSize(o.getBicycleSize().getName());
                    return model;
                })
                .collect(Collectors.toList());
    }
}
