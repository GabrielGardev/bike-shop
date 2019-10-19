package bikeshop.domain.models.binding;

import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

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

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<String> getBicycleSize() {
        return bicycleSize;
    }

    public void setBicycleSize(List<String> bicycleSize) {
        this.bicycleSize = bicycleSize;
    }

    public String getFrame() {
        return frame;
    }

    public void setFrame(String frame) {
        this.frame = frame;
    }

    public String getFork() {
        return fork;
    }

    public void setFork(String fork) {
        this.fork = fork;
    }

    public String getBreaks() {
        return breaks;
    }

    public void setBreaks(String breaks) {
        this.breaks = breaks;
    }

    public String getTyres() {
        return tyres;
    }

    public void setTyres(String tyres) {
        this.tyres = tyres;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }
}
