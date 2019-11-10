package bikeshop.domain.models.binding;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

import static bikeshop.common.Constants.*;

public class BicycleAddBindingModel {

    private String make;
    private String model;
    private String description;
    private String color;
    private BigDecimal price;
    private MultipartFile image;
    private String category;
    private List<String> bicycleSize;
    private String frame;
    private String fork;
    private String breaks;
    private String tyres;
    private String seat;

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

    @NotNull(message = NULL_BICYCLE_IMAGE_MESSAGE)
    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    @NotEmpty(message = EMPTY_CATEGORY_MESSAGE)
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @NotEmpty(message = EMPTY_BICYCLE_SIZE_MESSAGE)
    public List<String> getBicycleSize() {
        return bicycleSize;
    }

    public void setBicycleSize(List<String> bicycleSize) {
        this.bicycleSize = bicycleSize;
    }

    @NotNull(message = NULL_BICYCLE_FRAME_MESSAGE)
    @NotEmpty(message = EMPTY_BICYCLE_FRAME_MESSAGE)
    @Length(min = 1, max = 255, message = INVALID_BICYCLE_FRAME_LENGTH_MESSAGE)
    public String getFrame() {
        return frame;
    }

    public void setFrame(String frame) {
        this.frame = frame;
    }

    @NotNull(message = NULL_BICYCLE_FORK_MESSAGE)
    @NotEmpty(message = EMPTY_BICYCLE_FORK_MESSAGE)
    @Length(min = 1, max = 255, message = INVALID_BICYCLE_FORK_LENGTH_MESSAGE)
    public String getFork() {
        return fork;
    }

    public void setFork(String fork) {
        this.fork = fork;
    }

    @NotNull(message = NULL_BICYCLE_BREAKS_MESSAGE)
    @NotEmpty(message = EMPTY_BICYCLE_BREAKS_MESSAGE)
    @Length(min = 1, max = 255, message = INVALID_BICYCLE_BREAKS_LENGTH_MESSAGE)
    public String getBreaks() {
        return breaks;
    }

    public void setBreaks(String breaks) {
        this.breaks = breaks;
    }

    @NotNull(message = NULL_BICYCLE_TYRES_MESSAGE)
    @NotEmpty(message = EMPTY_BICYCLE_TYRES_MESSAGE)
    @Length(min = 1, max = 255, message = INVALID_BICYCLE_TYRES_LENGTH_MESSAGE)
    public String getTyres() {
        return tyres;
    }

    public void setTyres(String tyres) {
        this.tyres = tyres;
    }

    @NotNull(message = NULL_BICYCLE_SEAT_MESSAGE)
    @NotEmpty(message = EMPTY_BICYCLE_SEAT_MESSAGE)
    @Length(min = 1, max = 255, message = INVALID_BICYCLE_SEAT_LENGTH_MESSAGE)
    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }
}
