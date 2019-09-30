package bikeshop.domain.entities;

import javax.persistence.*;

@Entity
@Table(name = "images")
public class Image extends BaseEntity {

    private String url;
    private Bicycle bicycle;

    @Column(name = "image_url", nullable = false)
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @ManyToOne
    @JoinColumn(name = "bicycle_id", referencedColumnName = "id")
    public Bicycle getBicycle() {
        return bicycle;
    }

    public void setBicycle(Bicycle bicycle) {
        this.bicycle = bicycle;
    }
}
