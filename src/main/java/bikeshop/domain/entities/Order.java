package bikeshop.domain.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

    private List<OrderItem> bicycles;
    private User user;
    private BigDecimal totalPrice;
    private LocalDateTime finishedOn;

    @ManyToMany(targetEntity = OrderItem.class, cascade = CascadeType.MERGE)
    @JoinTable(name = "orders_bicycles",
            joinColumns = @JoinColumn(name = "order_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "bicycle_id", referencedColumnName = "id"))
    public List<OrderItem> getBicycles() {
        return bicycles;
    }

    public void setBicycles(List<OrderItem> bicycles) {
        this.bicycles = bicycles;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
