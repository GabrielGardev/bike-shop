package bikeshop.domain.models.binding;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import static bikeshop.common.Constants.*;

public class CategoryBindingModel {

    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @NotNull(message = NULL_CATEGORY_MESSAGE)
    @NotEmpty(message = EMPTY_CATEGORY_MESSAGE)
    @Length(min = 2, max = 15, message = INVALID_CATEGORY_LENGTH_MESSAGE)
    @Pattern(regexp = "^[A-Z]+", message = INVALID_CATEGORY_CAPITAL_CASE_MESSAGE)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
