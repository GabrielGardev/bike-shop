package bikeshop.domain.models.service;

import java.math.BigDecimal;

public class OrderItemServiceModel extends BaseServiceModel{

    private BicycleServiceModel bicycle;
    private BigDecimal price;
    private Integer quantity;
    private String bicycleSize;

    public BicycleServiceModel getBicycle() {
        return bicycle;
    }

    public void setBicycle(BicycleServiceModel bicycle) {
        this.bicycle = bicycle;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getBicycleSize() {
        return bicycleSize;
    }

    public void setBicycleSize(String bicycleSize) {
        this.bicycleSize = bicycleSize;
    }
}
