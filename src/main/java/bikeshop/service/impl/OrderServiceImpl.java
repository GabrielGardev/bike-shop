package bikeshop.service.impl;

import bikeshop.domain.entities.Bicycle;
import bikeshop.domain.entities.BicycleSize;
import bikeshop.domain.entities.Order;
import bikeshop.domain.entities.User;
import bikeshop.domain.models.service.BicycleServiceModel;
import bikeshop.domain.models.service.OrderServiceModel;
import bikeshop.domain.models.service.UserServiceModel;
import bikeshop.repository.BicycleRepository;
import bikeshop.repository.BicycleSizeRepository;
import bikeshop.repository.OrderRepository;
import bikeshop.repository.UserRepository;
import bikeshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static bikeshop.common.Constants.*;

@Service
public class OrderServiceImpl implements OrderService {

    private final BicycleServiceImpl bicycleService;
    private final BicycleRepository bicycleRepository;
    private final UserRepository userRepository;
    private final UserServiceImpl userService;
    private final OrderRepository orderRepository;
    private final BicycleSizeRepository sizeRepository;

    @Autowired
    public OrderServiceImpl(BicycleServiceImpl bicycleService, BicycleRepository bicycleRepository, UserRepository userRepository, UserServiceImpl userService, OrderRepository orderRepository, BicycleSizeRepository sizeRepository) {
        this.bicycleService = bicycleService;
        this.bicycleRepository = bicycleRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.orderRepository = orderRepository;
        this.sizeRepository = sizeRepository;
    }

    @Override
    public OrderServiceModel viewOrder(String bicycleId, String username, OrderServiceModel order) {
        BicycleServiceModel bicycle = bicycleService.findById(bicycleId);
        UserServiceModel user = userService.findUserByUsername(username);

        order.setBicycle(bicycle);
        order.setUser(user);

        BigDecimal totalPrice = bicycle.getPrice().multiply(BigDecimal.valueOf(order.getQuantity()));
        order.setTotalPrice(totalPrice);

        return order;
    }

    @Override
    public void createOrder(String bicycleId, String bicycleSizeName, String quantity, String totalPrice, String username) {
        Bicycle bicycle = bicycleRepository.findById(bicycleId)
                .orElseThrow(() -> new IllegalArgumentException(INCORRECT_ID));

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(USERNAME_NOT_FOUND));

        BicycleSize bicycleSize = sizeRepository.findByName(bicycleSizeName)
                .orElseThrow(() -> new IllegalArgumentException(INCORRECT_SIZE_NAME));

        Order order = new Order();
        order.setBicycle(bicycle);
        order.setUser(user);
        order.setBicycleSize(bicycleSize);
        order.setQuantity(Integer.valueOf(quantity));
        order.setTotalPrice(BigDecimal.valueOf(Double.parseDouble(totalPrice)));
        orderRepository.save(order);
    }
}
