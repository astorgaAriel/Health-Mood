package cl.healthmood.Health.Mood.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageResponse {

    private Integer imageId;
    private String imageUrl;
    private Integer pedido;
    private String isPrimary;
    private Integer productId;
    private String productName;
}
