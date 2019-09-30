package bikeshop.domain.entities;

import javax.persistence.*;

@Entity
@Table(name = "sizes")
public class BikeSize extends BaseEntity{

    private String name;

    @Column(nullable = false, unique = true, updatable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
