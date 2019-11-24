package bikeshop.domain.models.binding;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

import static bikeshop.common.Constants.*;

public class CartItemBindingModel {

    private String bicycleSize;
    private int quantity;

    @NotNull(message = NULL_BICYCLE_SIZE_MESSAGE)
    @NotEmpty(message = EMPTY_BICYCLE_SIZE_MESSAGE)
    public String getBicycleSize() {
        return bicycleSize;
    }

    public void setBicycleSize(String bicycleSize) {
        this.bicycleSize = bicycleSize;
    }

    @NotNull(message = NULL_ORDER_QUANTITY_MESSAGE)
    @Min(value = 1, message = MIN_ORDER_QUANTITY_MESSAGE)
    @Max(value = 100, message = MAX_ORDER_QUANTITY_MESSAGE)
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
