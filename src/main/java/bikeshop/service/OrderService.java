package bikeshop.service;

import bikeshop.domain.models.service.OrderServiceModel;
import bikeshop.error.BicycleNotFoundException;

import java.util.Collection;
import java.util.List;

public interface OrderService {

    void createOrder(OrderServiceModel order);

    List<OrderServiceModel> findAllOrders();

    List<OrderServiceModel> findByCustomerName(String username);

    OrderServiceModel findOrderById(String id);
}
