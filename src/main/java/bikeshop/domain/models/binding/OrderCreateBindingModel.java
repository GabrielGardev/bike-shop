package bikeshop.domain.models.binding;

import java.math.BigDecimal;

public class OrderCreateBindingModel {

    private String bicycleSize;
    private int quantity;
    private BigDecimal totalPrice;

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
}
