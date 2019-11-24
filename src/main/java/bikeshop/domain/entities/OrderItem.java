package bikeshop.domain.entities;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
public class OrderItem extends BaseEntity {

    private Bicycle bicycle;
    private BigDecimal price;
    private Integer quantity;
    private BicycleSize bicycleSize;

    @ManyToOne
    @JoinColumn(name = "bicycle_id", referencedColumnName = "id")
    public Bicycle getBicycle() {
        return bicycle;
    }

    public void setBicycle(Bicycle bicycle) {
        this.bicycle = bicycle;
    }

    @Column
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Column
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @ManyToOne
    @JoinColumn(name = "bicycle_size_id", referencedColumnName = "id")
    public BicycleSize getBicycleSize() {
        return bicycleSize;
    }

    public void setBicycleSize(BicycleSize bicycleSize) {
        this.bicycleSize = bicycleSize;
    }
}
