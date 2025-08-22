package cl.healthmood.Health.Mood.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemResponse {

    private Integer itemId;
    private Integer pedidoId;
    private Integer productId;
    private String productName;
    private Integer quantity;
    private Integer listPrice;
    private Integer discount;
    private Integer finalPrice;
}
