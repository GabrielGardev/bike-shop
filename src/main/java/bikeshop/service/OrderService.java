package bikeshop.service;

import bikeshop.domain.models.service.OrderServiceModel;
import bikeshop.error.BicycleNotFoundException;

import java.util.Collection;
import java.util.List;

public interface OrderService {

    OrderServiceModel viewOrder(String bicycleId, String username, OrderServiceModel model);

    void createOrder(String bicycleId, String bicycleSize, String quantity, String totalPrice, String username);

    List<OrderServiceModel> findAllOrders();

    List<OrderServiceModel> findByCustomerName(String username);
}
