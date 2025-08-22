package cl.healthmood.Health.Mood.service;

import cl.healthmood.Health.Mood.dto.ImageRequest;
import cl.healthmood.Health.Mood.dto.ImageResponse;
import cl.healthmood.Health.Mood.mapper.ImageMapper;
import cl.healthmood.Health.Mood.model.Image;
import cl.healthmood.Health.Mood.model.Product;
import cl.healthmood.Health.Mood.repository.ImageRepository;
import cl.healthmood.Health.Mood.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final ProductRepository productRepository;
    private final ImageMapper imageMapper;

    @Override
    @Transactional(readOnly = true)
    public List<ImageResponse> getAllImages() {
        log.debug("Obteniendo todas las imágenes");
        return imageRepository.findAll().stream()
                .map(imageMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ImageResponse> getImageById(Integer id) {
        log.debug("Buscando imagen por ID: {}", id);
        return imageRepository.findById(id)
                .map(imageMapper::toResponse);
    }

    @Override
    @Transactional
    public ImageResponse saveImage(ImageRequest imageRequest) {
        log.debug("Guardando nueva imagen para producto ID: {}", imageRequest.getProductId());
        validateImageRequest(imageRequest);

        // Verificar que el producto existe
        Product product = productRepository.findById(imageRequest.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado con ID: " + imageRequest.getProductId()));

        Image image = imageMapper.toEntity(imageRequest);
        image.setProduct(product);
        
        Image savedImage = imageRepository.save(image);
        return imageMapper.toResponse(savedImage);
    }

    @Override
    @Transactional
    public ImageResponse updateImage(Integer id, ImageRequest imageRequest) {
        log.debug("Actualizando imagen ID: {}", id);
        validateImageRequest(imageRequest);

        return imageRepository.findById(id)
                .map(existingImage -> {
                    // Verificar que el producto existe si se está cambiando
                    if (!existingImage.getProduct().getProductId().equals(imageRequest.getProductId())) {
                        Product product = productRepository.findById(imageRequest.getProductId())
                                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado con ID: " + imageRequest.getProductId()));
                        existingImage.setProduct(product);
                    }
                    
                    imageMapper.updateEntityFromRequest(imageRequest, existingImage);
                    Image updatedImage = imageRepository.save(existingImage);
                    return imageMapper.toResponse(updatedImage);
                })
                .orElseThrow(() -> new RuntimeException("Imagen no encontrada con ID: " + id));
    }

    @Override
    @Transactional
    public void deleteImage(Integer id) {
        log.debug("Eliminando imagen ID: {}", id);
        
        if (!imageRepository.existsById(id)) {
            throw new RuntimeException("Imagen no encontrada con ID: " + id);
        }
        
        imageRepository.deleteById(id);
    }

    private void validateImageRequest(ImageRequest imageRequest) {
        if (imageRequest.getImageUrl() == null || imageRequest.getImageUrl().trim().isEmpty()) {
            throw new IllegalArgumentException("La URL de la imagen es obligatoria");
        }

        if (imageRequest.getPedido() == null) {
            throw new IllegalArgumentException("El número de pedido es obligatorio");
        }

        if (imageRequest.getIsPrimary() == null || imageRequest.getIsPrimary().trim().isEmpty()) {
            throw new IllegalArgumentException("El campo isPrimary es obligatorio");
        }

        if (imageRequest.getProductId() == null) {
            throw new IllegalArgumentException("El ID del producto es obligatorio");
        }
    }
}