package cl.healthmood.Health.Mood.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageRequest {

    @NotBlank(message = "Image URL is required")
    @Size(max = 150, message = "Image URL must not exceed 150 characters")
    private String imageUrl;

    @NotNull(message = "Pedido number is required")
    private Integer pedido;

    @NotBlank(message = "Is primary field is required")
    @Size(max = 1, message = "Is primary must be 1 character")
    private String isPrimary;

    @NotNull(message = "Product ID is required")
    private Integer productId;
}
