package cl.healthmood.Health.Mood.repository;

import cl.healthmood.Health.Mood.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    // Puedes agregar métodos personalizados si los necesitas
}