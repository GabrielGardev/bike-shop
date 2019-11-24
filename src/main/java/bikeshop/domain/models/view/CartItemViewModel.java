package bikeshop.domain.models.view;

import java.io.Serializable;

public class CartItemViewModel implements Serializable {

    private BicycleViewModel bicycle;
    private int quantity;

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
}
