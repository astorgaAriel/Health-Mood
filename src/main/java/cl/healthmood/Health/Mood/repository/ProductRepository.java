package cl.healthmood.Health.Mood.repository;

import cl.healthmood.Health.Mood.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findByNameContainingIgnoreCase(String name);

    List<Product> findByCategoryCategoryId(Integer categoryId);

    List<Product> findByPriceBetween(Integer minPrice, Integer maxPrice);

    List<Product> findByPriceGreaterThanEqual(Integer price);

    List<Product> findByPriceLessThanEqual(Integer price);

    List<Product> findByOrderByPriceAsc();

    List<Product> findByOrderByPriceDesc();

    List<Product> findByOrderByNameAsc();

    @Query("SELECT p FROM Product p WHERE p.name LIKE %:name% AND p.category.categoryId = :categoryId")
    List<Product> findByNameAndCategory(@Param("name") String name, @Param("categoryId") Integer categoryId);

    @Query("SELECT p FROM Product p WHERE p.price BETWEEN :minPrice AND :maxPrice AND p.category.categoryId = :categoryId")
    List<Product> findByPriceRangeAndCategory(@Param("minPrice") Integer minPrice,
                                              @Param("maxPrice") Integer maxPrice,
                                              @Param("categoryId") Integer categoryId);
}