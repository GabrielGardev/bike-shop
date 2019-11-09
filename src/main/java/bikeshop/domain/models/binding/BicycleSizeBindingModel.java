package bikeshop.domain.models.binding;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import static bikeshop.common.Constants.*;

public class BicycleSizeBindingModel {

    private String name;

    @NotNull(message = NULL_BICYCLE_SIZE_MESSAGE)
    @NotEmpty(message = EMPTY_BICYCLE_SIZE_MESSAGE)
    @Length(max = 2, message = INVALID_BICYCLE_SIZE_LENGTH_MESSAGE)
    @Pattern(regexp = "^[A-Z]+", message = INVALID_BICYCLE_SIZE_CAPITAL_CASE_MESSAGE)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
