package cl.healthmood.Health.Mood.service;

import cl.healthmood.Health.Mood.dto.ImageRequest;
import cl.healthmood.Health.Mood.dto.ImageResponse;

import java.util.List;
import java.util.Optional;

public interface ImageService {
    List<ImageResponse> getAllImages();
    Optional<ImageResponse> getImageById(Integer id);
    ImageResponse saveImage(ImageRequest imageRequest);
    ImageResponse updateImage(Integer id, ImageRequest imageRequest);
    void deleteImage(Integer id);
}