package bikeshop.domain.models.binding;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import static bikeshop.common.Constants.*;

public class ComponentEditBindingModel implements Comparable<ComponentEditBindingModel>{

    private String id;
    private String type;
    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @NotNull(message = NULL_COMPONENT_MESSAGE)
    @NotEmpty(message = EMPTY_COMPONENT_MESSAGE)
    @Length(min = 1, max = 255, message = INVALID_COMPONENT_LENGTH_MESSAGE)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int compareTo(ComponentEditBindingModel o) {
        return type.compareTo(o.getType());
    }
}
