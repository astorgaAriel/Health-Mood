package cl.healthmood.Health.Mood.mapper;

import cl.healthmood.Health.Mood.dto.ProductRequest;
import cl.healthmood.Health.Mood.dto.ProductResponse;
import cl.healthmood.Health.Mood.model.Product;
import cl.healthmood.Health.Mood.model.Category;
import cl.healthmood.Health.Mood.model.Image;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductMapper {

    public Product toEntity(ProductRequest request) {
        if (request == null) {
            return null;
        }

        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .build();

        if (request.getCategoryId() != null) {
            Category category = new Category();
            category.setCategoryId(request.getCategoryId());
            product.setCategory(category);
        }

        return product;
    }

    public ProductResponse toResponse(Product product) {
        if (product == null) {
            return null;
        }

        ProductResponse.ProductResponseBuilder responseBuilder = ProductResponse.builder()
                .productId(product.getProductId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice());

        // Mapear categoría si existe
        if (product.getCategory() != null) {
            ProductResponse.CategoryResponse categoryResponse = ProductResponse.CategoryResponse.builder()
                    .categoryId(product.getCategory().getCategoryId())
                    .name(product.getCategory().getName())
                    .description(product.getCategory().getDescription())
                    .build();
            responseBuilder.category(categoryResponse);
        }

        // Mapear imágenes si existen
        if (product.getImages() != null && !product.getImages().isEmpty()) {
            List<ProductResponse.ImageResponse> imageResponses = product.getImages().stream()
                    .map(this::mapImage)
                    .collect(Collectors.toList());
            responseBuilder.images(imageResponses);
        }

        return responseBuilder.build();
    }

    public List<ProductResponse> toResponseList(List<Product> products) {
        if (products == null) {
            return null;
        }
        return products.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public void updateEntityFromRequest(Product product, ProductRequest request) {
        if (request == null || product == null) {
            return;
        }

        if (request.getName() != null && !request.getName().trim().isEmpty()) {
            product.setName(request.getName().trim());
        }
        if (request.getDescription() != null) {
            product.setDescription(request.getDescription());
        }
        if (request.getPrice() != null && request.getPrice() >= 0) {
            product.setPrice(request.getPrice());
        }
        if (request.getCategoryId() != null) {
            Category category = new Category();
            category.setCategoryId(request.getCategoryId());
            product.setCategory(category);
        }
    }

    private ProductResponse.ImageResponse mapImage(Image image) {
        if (image == null) {
            return null;
        }
        return ProductResponse.ImageResponse.builder()
                .imageId(image.getImageId())
                .imageUrl(image.getImageUrl())
               // .altText(image.getAltText())
            //    .isPrimary(image.isPrimary())
                .build();
    }
}