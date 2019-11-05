package bikeshop.domain.models.view;

import java.math.BigDecimal;

public class OrderViewModel {

    private String id;
    private BicycleViewModel bicycle;
    private UserProfileViewModel user;
    private String bicycleSize;
    private int quantity;
    private BigDecimal totalPrice;
    private String finishedOn;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BicycleViewModel getBicycle() {
        return bicycle;
    }

    public void setBicycle(BicycleViewModel bicycle) {
        this.bicycle = bicycle;
    }

    public UserProfileViewModel getUser() {
        return user;
    }

    public void setUser(UserProfileViewModel user) {
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

    public String getDate() {
        return finishedOn;
    }

    public void setDate(String finishedOn) {
        this.finishedOn = finishedOn;
    }
}
