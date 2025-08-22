package cl.healthmood.Health.Mood.repository;

import cl.healthmood.Health.Mood.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Integer> {
    // Métodos personalizados si necesitas
}