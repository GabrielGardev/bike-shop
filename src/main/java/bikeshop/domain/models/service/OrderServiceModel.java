package bikeshop.domain.models.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderServiceModel extends BaseServiceModel {

    private BicycleServiceModel bicycle;
    private UserServiceModel user;
    private String bicycleSize;
    private int quantity;
    private BigDecimal totalPrice;
    private LocalDateTime finishedOn;

    public BicycleServiceModel getBicycle() {
        return bicycle;
    }

    public void setBicycle(BicycleServiceModel bicycle) {
        this.bicycle = bicycle;
    }

    public UserServiceModel getUser() {
        return user;
    }

    public void setUser(UserServiceModel user) {
        this.user = user;
    }

    public String getBicycleSize() {
        return bicycleSize;
    }

    public void setBicycleSize(String bicycleSize) {
        this.bicycleSize = bicycleSize;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDateTime getFinishedOn() {
        return finishedOn;
    }

    public void setFinishedOn(LocalDateTime finishedOn) {
        this.finishedOn = finishedOn;
    }
}
