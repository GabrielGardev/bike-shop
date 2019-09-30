package bikeshop.domain.entities;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "components")
public class Component extends BaseEntity{

    private String type;
    private String make;
    private String model;
    private BigDecimal price;

    @Column(nullable = false, updatable = false)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Column(nullable = false, updatable = false)
    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    @Column(nullable = false, updatable = false)
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Column(nullable = false)
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
