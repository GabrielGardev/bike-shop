package bikeshop.domain.models.view;

import java.io.Serializable;

public class CartItemViewModel implements Serializable {

    private BicycleViewModel bicycle;
    private int quantity;
    private String bicycleSize;

    public BicycleViewModel getBicycle() {
        return bicycle;
    }

    public void setBicycle(BicycleViewModel bicycle) {
        this.bicycle = bicycle;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getBicycleSize() {
        return bicycleSize;
    }

    public void setBicycleSize(String bicycleSize) {
        this.bicycleSize = bicycleSize;
    }
}
