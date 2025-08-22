package cl.healthmood.Health.Mood.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponse {

    private Integer productId;
    private String name;
    private String description;
    private Integer price;
    private CategoryResponse category;
    private List<ImageResponse> images;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CategoryResponse {
        private Integer categoryId;
        private String name;
        private String description;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ImageResponse {
        private Integer imageId;
        private String imageUrl;
      //  private String altText;
      //  private boolean isPrimary;
    }
}