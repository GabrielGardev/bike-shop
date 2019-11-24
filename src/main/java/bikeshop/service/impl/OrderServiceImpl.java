package bikeshop.service.impl;

import bikeshop.domain.entities.Order;
import bikeshop.domain.entities.OrderItem;
import bikeshop.domain.models.service.OrderItemServiceModel;
import bikeshop.domain.models.service.OrderServiceModel;
import bikeshop.error.BicycleSizeNotFoundException;
import bikeshop.error.OrderNotFoundException;
import bikeshop.repository.*;
import bikeshop.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static bikeshop.common.Constants.INCORRECT_BICYCLE_SIZE;
import static bikeshop.common.Constants.INCORRECT_ORDER;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final BicycleSizeRepository bicycleSizeRepository;
    private final OrderItemRepository orderItemRepository;
    private final ModelMapper mapper;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, BicycleSizeRepository bicycleSizeRepository, OrderItemRepository orderItemRepository, ModelMapper mapper) {
        this.orderRepository = orderRepository;
        this.bicycleSizeRepository = bicycleSizeRepository;
        this.orderItemRepository = orderItemRepository;
        this.mapper = mapper;
    }

    @Override
    public void createOrder(OrderServiceModel orderServiceModel) {
        orderServiceModel.setFinishedOn(LocalDateTime.now());
        Order order = mapper.map(orderServiceModel, Order.class);
        order.getBicycles()
                .forEach(b -> {
                    this.convertBicycleSize(orderServiceModel, b);
                    orderItemRepository.save(b);
                });

        orderRepository.save(order);
    }

    @Override
    public List<OrderServiceModel> findAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(o -> mapper.map(o, OrderServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderServiceModel> findByCustomerName(String username) {
        return orderRepository.findAllByUserUsername(username)
                .stream()
                .map(o -> mapper.map(o, OrderServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public OrderServiceModel findOrderById(String id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(INCORRECT_ORDER));

        OrderServiceModel serviceModel = mapper.map(order, OrderServiceModel.class);
        serviceModel.getBicycles()
                .forEach(b -> {
                    for (OrderItem bicycle : order.getBicycles()) {
                        if(b.getBicycle().getId().equals(bicycle.getBicycle().getId())){
                            b.setBicycleSize(bicycle.getBicycleSize().getName());
                            b.getBicycle().setCategory(bicycle.getBicycle().getCategory().getName());
                            order.getBicycles().remove(bicycle);
                            break;
                        }
                    }
                });
        return serviceModel;
    }

    private void convertBicycleSize(OrderServiceModel model, OrderItem result) {
        for (OrderItemServiceModel bicycle : model.getBicycles()) {
            if(result.getBicycle().getId().equals(bicycle.getBicycle().getId())){
                result.setBicycleSize(bicycleSizeRepository.findByName(bicycle.getBicycleSize())
                        .orElseThrow(() -> new BicycleSizeNotFoundException(INCORRECT_BICYCLE_SIZE)));
                model.getBicycles().remove(bicycle);
                break;
            }
        }
    }
}
