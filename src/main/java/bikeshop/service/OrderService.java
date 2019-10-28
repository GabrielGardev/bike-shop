package bikeshop.service;

import bikeshop.domain.models.service.OrderServiceModel;

public interface OrderService {

    OrderServiceModel viewOrder(String bicycleId, String username, OrderServiceModel model);

    void createOrder(String bicycleId, String bicycleSize, String quantity, String totalPrice, String username);
}
