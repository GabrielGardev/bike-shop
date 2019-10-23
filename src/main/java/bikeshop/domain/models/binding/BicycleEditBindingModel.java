package bikeshop.domain.models.binding;

import java.math.BigDecimal;
import java.util.Set;

public class BicycleEditBindingModel {

    private String make;
    private String model;
    private String description;
    private String color;
    private BigDecimal price;
    private String category;
    private Set<String> bicycleSize;

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Set<String> getBicycleSize() {
        return bicycleSize;
    }

    public void setBicycleSize(Set<String> bicycleSize) {
        this.bicycleSize = bicycleSize;
    }
}
