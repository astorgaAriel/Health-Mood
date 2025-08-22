package cl.healthmood.Health.Mood.controller;

import cl.healthmood.Health.Mood.dto.ImageRequest;
import cl.healthmood.Health.Mood.dto.ImageResponse;
import cl.healthmood.Health.Mood.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/images")
@RequiredArgsConstructor
@Validated
@Slf4j
@CrossOrigin(origins = "*")
public class ImageController {

    private final ImageService imageService;

    // Todos pueden ver las imágenes (sin restricción de autenticación)
    @GetMapping
    public ResponseEntity<List<ImageResponse>> getAllImages() {
        log.info("Getting all images");
        List<ImageResponse> images = imageService.getAllImages();
        return ResponseEntity.ok(images);
    }

    // Todos pueden ver una imagen específica (sin restricción de autenticación)
    @GetMapping("/{id}")
    public ResponseEntity<ImageResponse> getImageById(@PathVariable Integer id) {
        log.info("Getting image by ID: {}", id);
        return imageService.getImageById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Solo ADMIN puede crear imágenes
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ImageResponse> createImage(@Valid @RequestBody ImageRequest imageRequest) {
        log.info("Creating new image for product ID: {}", imageRequest.getProductId());
        try {
            ImageResponse savedImage = imageService.saveImage(imageRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedImage);
        } catch (IllegalArgumentException e) {
            log.error("Validation error creating image: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    // Solo ADMIN puede actualizar imágenes
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ImageResponse> updateImage(@PathVariable Integer id, @Valid @RequestBody ImageRequest imageRequest) {
        log.info("Updating image ID: {}", id);
        try {
            ImageResponse updatedImage = imageService.updateImage(id, imageRequest);
            return ResponseEntity.ok(updatedImage);
        } catch (IllegalArgumentException e) {
            log.error("Validation error updating image: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            log.error("Image not found with ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    // Solo ADMIN puede eliminar imágenes
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteImage(@PathVariable Integer id) {
        log.info("Deleting image ID: {}", id);
        try {
            imageService.deleteImage(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            log.error("Image not found with ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }
}