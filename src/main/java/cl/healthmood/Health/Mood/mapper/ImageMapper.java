package cl.healthmood.Health.Mood.mapper;

import cl.healthmood.Health.Mood.dto.ImageRequest;
import cl.healthmood.Health.Mood.dto.ImageResponse;
import cl.healthmood.Health.Mood.model.Image;
import cl.healthmood.Health.Mood.model.Product;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ImageMapper {

    public Image toEntity(ImageRequest imageRequest) {
        if (imageRequest == null) {
            return null;
        }

        return Image.builder()
                .imageUrl(imageRequest.getImageUrl())
                .pedido(imageRequest.getPedido())
                .isPrimary(imageRequest.getIsPrimary())
                .product(Product.builder().productId(imageRequest.getProductId()).build())
                .build();
    }

    public ImageResponse toResponse(Image image) {
        if (image == null) {
            return null;
        }

        return ImageResponse.builder()
                .imageId(image.getImageId())
                .imageUrl(image.getImageUrl())
                .pedido(image.getPedido())
                .isPrimary(image.getIsPrimary())
                .productId(image.getProduct() != null ? image.getProduct().getProductId() : null)
                .productName(image.getProduct() != null ? image.getProduct().getName() : null)
                .build();
    }

    public List<ImageResponse> toResponseList(List<Image> images) {
        if (images == null) {
            return null;
        }
        return images.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public void updateEntityFromRequest(ImageRequest imageRequest, Image image) {
        if (imageRequest != null && image != null) {
            image.setImageUrl(imageRequest.getImageUrl());
            image.setPedido(imageRequest.getPedido());
            image.setIsPrimary(imageRequest.getIsPrimary());
        }
    }
}
