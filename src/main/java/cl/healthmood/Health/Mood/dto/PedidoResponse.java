package cl.healthmood.Health.Mood.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PedidoResponse {

    private Integer pedidoId;
    private Integer customerId;
    private String customerName;
    private String orderStatus;
    private LocalDate orderDate;
    private LocalDate requiredDate;
    private LocalDate shippedDate;
    private List<OrderItemResponse> orderItems;
    private List<PaymentResponse> payments;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class OrderItemResponse {
        private Integer orderItemId;
        private Integer productId;
        private String productName;
        private Integer quantity;
        private Double unitPrice;
        private Double totalPrice;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PaymentResponse {
        private Integer paymentId;
        private String paymentMethod; // No existe en Payment, se pone por defecto
        private Double amount; // Convertido de Integer a Double
        private LocalDate paymentDate;
        private String status; // No existe en Payment, se pone por defecto
    }
}