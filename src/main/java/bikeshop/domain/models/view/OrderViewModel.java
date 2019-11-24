package bikeshop.domain.models.view;

import java.math.BigDecimal;
import java.util.List;

public class OrderViewModel {

    private String id;
    private List<BicycleViewModel> bicycles;
    private UserProfileViewModel user;
    private BigDecimal totalPrice;
    private String finishedOn;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<BicycleViewModel> getBicycles() {
        return bicycles;
    }

    public void setBicycles(List<BicycleViewModel> bicycles) {
        this.bicycles = bicycles;
    }

    public UserProfileViewModel getUser() {
        return user;
    }

    public void setUser(UserProfileViewModel user) {
        this.user = user;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getDate() {
        return finishedOn;
    }

    public void setDate(String finishedOn) {
        this.finishedOn = finishedOn;
    }
}
