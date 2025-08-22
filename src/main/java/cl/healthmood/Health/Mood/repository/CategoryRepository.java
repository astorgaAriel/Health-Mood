package cl.healthmood.Health.Mood.repository;

import cl.healthmood.Health.Mood.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    List<Category> findByNameContainingIgnoreCase(String name);

    Optional<Category> findByName(String name);

    boolean existsByName(String name);

    List<Category> findByOrderByNameAsc();

    List<Category> findByOrderByNameDesc();

    @Query("SELECT c FROM Category c LEFT JOIN FETCH c.products")
    List<Category> findAllWithProducts();

    @Query("SELECT c FROM Category c WHERE SIZE(c.products) > 0")
    List<Category> findCategoriesWithProducts();

    @Query("SELECT c FROM Category c WHERE SIZE(c.products) = 0")
    List<Category> findEmptyCategories();

    @Query("SELECT c FROM Category c WHERE c.name LIKE %:name% AND SIZE(c.products) > 0")
    List<Category> findByNameContainingIgnoreCaseAndHasProducts(String name);

    @Query("SELECT c FROM Category c ORDER BY SIZE(c.products) DESC")
    List<Category> findAllOrderByProductCountDesc();

    @Query("SELECT COUNT(c) FROM Category c WHERE SIZE(c.products) > 0")
    long countCategoriesWithProducts();
}