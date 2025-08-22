package cl.healthmood.Health.Mood.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemRequest {

    @NotNull(message = "Pedido ID is required")
    private Integer pedidoId;

    @NotNull(message = "Product ID is required")
    private Integer productId;

    @NotNull(message = "Quantity is required")
    @PositiveOrZero(message = "Quantity must be positive or zero")
    private Integer quantity;

    @NotNull(message = "List price is required")
    @PositiveOrZero(message = "List price must be positive or zero")
    private Integer listPrice;

    @PositiveOrZero(message = "Discount must be positive or zero")
    private Integer discount;
}
