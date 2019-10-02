package bikeshop.domain.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "bikes")
public class Bicycle extends BaseEntity{

    private String make;
    private String model;
    private String description;
    private String color;
    private BigDecimal price;
    private Category category;
    private Set<BicycleSize> bicycleSize;
    private Set<Image> images;
    private Set<Component> components;

    @Column(nullable = false)
    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    @Column(nullable = false)
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Column(nullable = false, columnDefinition = "TEXT")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(nullable = false)
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Column(nullable = false)
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @ManyToOne(targetEntity = Category.class)
    @JoinColumn(name = "category", referencedColumnName = "id", nullable = false)
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @ManyToMany(targetEntity = BicycleSize.class)
    @JoinTable(
            name = "bikes_sizes",
            joinColumns = @JoinColumn(
                    name = "bicycle_id",
                    referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "size_id",
                    referencedColumnName = "id"
            )
    )
    public Set<BicycleSize> getBicycleSize() {
        return bicycleSize;
    }

    public void setBicycleSize(Set<BicycleSize> bicycleSize) {
        this.bicycleSize = bicycleSize;
    }

    @OneToMany(mappedBy = "bicycle")
    public Set<Image> getImages() {
        return images;
    }

    public void setImages(Set<Image> images) {
        this.images = images;
    }

    @ManyToMany(targetEntity = Component.class)
    @JoinTable(
            name = "bikes_components",
            joinColumns = @JoinColumn(
                    name = "bicycle_id",
                    referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "component_id",
                    referencedColumnName = "id"
            )
    )
    public Set<Component> getComponents() {
        return components;
    }

    public void setComponents(Set<Component> components) {
        this.components = components;
    }
}
