package bikeshop.domain.models.binding;

public class OrderCreateBindingModel {

    private String bicycleSize;
    private int quantity;

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
}
