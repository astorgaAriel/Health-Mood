package cl.healthmood.Health.Mood.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentResponse {

    private Integer paymentId;
    private Integer customerId;
    private String customerName;
    private Integer pedidoId;
    private LocalDate paymentDate;
    private Integer amount;
}
