package bikeshop.domain.models.view;

import java.math.BigDecimal;
import java.util.Set;
public class BicycleViewModel {

    private String id;
    private String make;
    private String model;
    private String description;
    private String color;
    private BigDecimal price;
    private Double discount;
    private String imageUrl;
    private String category;
    private Set<String> bicycleSize;
    private Set<ComponentViewModel> components;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    public Set<ComponentViewModel> getComponents() {
        return components;
    }

    public void setComponents(Set<ComponentViewModel> components) {
        this.components = components;
    }
}
