package cl.healthmood.Health.Mood.service;

import cl.healthmood.Health.Mood.dto.ProductRequest;
import cl.healthmood.Health.Mood.dto.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    ProductResponse save(ProductRequest productRequest);

    Optional<ProductResponse> findById(Integer id);

    List<ProductResponse> findAll();

    Page<ProductResponse> findAll(Pageable pageable);

    ProductResponse update(Integer id, ProductRequest productRequest);

    void deleteById(Integer id);

    boolean existsById(Integer id);

    List<ProductResponse> findByNameContainingIgnoreCase(String name);

    List<ProductResponse> findByCategoryId(Integer categoryId);

    List<ProductResponse> findByPriceBetween(Integer minPrice, Integer maxPrice);

    List<ProductResponse> findByPriceGreaterThanEqual(Integer price);

    List<ProductResponse> findByPriceLessThanEqual(Integer price);

    long count();
}