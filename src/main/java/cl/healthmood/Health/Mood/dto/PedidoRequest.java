package cl.healthmood.Health.Mood.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PedidoRequest {

    @NotNull(message = "El ID del cliente es requerido")
    private Integer customerId;

    @NotBlank(message = "El estado del pedido es requerido")
    private String orderStatus;

    @NotNull(message = "La fecha del pedido es requerida")
    private LocalDate orderDate;

    @NotNull(message = "La fecha requerida es necesaria")
    private LocalDate requiredDate;

    private LocalDate shippedDate;
}