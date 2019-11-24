package bikeshop.domain.models.view;

import java.math.BigDecimal;

public class OrderItemViewModel {

    private String id;
    private BicycleViewModel bicycle;
    private BigDecimal price;
    private Integer quantity;
    private String bicycleSize;

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
