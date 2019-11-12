package bikeshop.domain.models.view;

import java.math.BigDecimal;
import java.util.Set;

public class BicycleByCategoryViewModel {

    private String id;
    private String make;
    private String model;
    private BigDecimal price;
    private Double discount;
    private String imageUrl;
    private Set<String> bicycleSize;

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

    public Set<String> getBicycleSize() {
        return bicycleSize;
    }

    public void setBicycleSize(Set<String> bicycleSize) {
        this.bicycleSize = bicycleSize;
    }
}
