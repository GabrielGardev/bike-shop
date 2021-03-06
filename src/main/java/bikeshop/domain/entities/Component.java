package bikeshop.domain.entities;

import javax.persistence.*;

@Entity
@Table(name = "components")
public class Component extends BaseEntity{

    private String type;
    private String description;

    @Column(nullable = false, updatable = false)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Column(nullable = false)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
