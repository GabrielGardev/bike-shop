package bikeshop.domain.models.binding;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Set;

import static bikeshop.common.Constants.*;
import static bikeshop.common.Constants.EMPTY_BICYCLE_MODEL_MESSAGE;

public class BicycleEditBindingModel {

    private String id;
    private String make;
    private String model;
    private String description;
    private String color;
    private BigDecimal price;
    private String category;
    private Set<String> bicycleSize;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @NotNull(message = NULL_BICYCLE_MAKE_MESSAGE)
    @NotEmpty(message = EMPTY_BICYCLE_MAKE_MESSAGE)
    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    @NotNull(message = NULL_BICYCLE_MODEL_MESSAGE)
    @NotEmpty(message = EMPTY_BICYCLE_MODEL_MESSAGE)
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @NotNull(message = NULL_BICYCLE_DESCRIPTION_MESSAGE)
    @NotEmpty(message = EMPTY_BICYCLE_DESCRIPTION_MESSAGE)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NotNull(message = NULL_BICYCLE_COLOR_MESSAGE)
    @NotEmpty(message = EMPTY_BICYCLE_COLOR_MESSAGE)
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @NotNull(message = NULL_BICYCLE_PRICE_MESSAGE)
    @Min(value = 0, message = MIN_BICYCLE_PRICE_MESSAGE)
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @NotEmpty(message = EMPTY_CATEGORY_MESSAGE)
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @NotEmpty(message = EMPTY_BICYCLE_SIZE_MESSAGE)
    public Set<String> getBicycleSize() {
        return bicycleSize;
    }

    public void setBicycleSize(Set<String> bicycleSize) {
        this.bicycleSize = bicycleSize;
    }
}
