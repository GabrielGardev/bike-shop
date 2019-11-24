package bikeshop.domain.models.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderServiceModel extends BaseServiceModel {

    private List<BicycleServiceModel> bicycles;
    private UserServiceModel user;
    private BigDecimal totalPrice;
    private LocalDateTime finishedOn;

    public List<BicycleServiceModel> getBicycles() {
        return bicycles;
    }

    public void setBicycles(List<BicycleServiceModel> bicycles) {
        this.bicycles = bicycles;
    }

    public UserServiceModel getUser() {
        return user;
    }

    public void setUser(UserServiceModel user) {
        this.user = user;
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
