package bikeshop.domain.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

    private Bicycle bicycle;
    private User user;
    private BicycleSize bicycleSize;
    private Integer quantity;
    private BigDecimal totalPrice;
    private LocalDateTime finishedOn;

    @ManyToOne
    @JoinColumn(name = "bicycle_id", referencedColumnName = "id")
    public Bicycle getBicycle() {
        return bicycle;
    }

    public void setBicycle(Bicycle bicycle) {
        this.bicycle = bicycle;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne
    @JoinColumn(name = "size_id", referencedColumnName = "id")
    public BicycleSize getBicycleSize() {
        return bicycleSize;
    }

    public void setBicycleSize(BicycleSize bicycleSize) {
        this.bicycleSize = bicycleSize;
    }

    @Column(nullable = false)
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Column(name = "total_price")
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Column(name = "finished_on")
    public LocalDateTime getFinishedOn() {
        return finishedOn;
    }

    public void setFinishedOn(LocalDateTime finishedOn) {
        this.finishedOn = finishedOn;
    }
}
