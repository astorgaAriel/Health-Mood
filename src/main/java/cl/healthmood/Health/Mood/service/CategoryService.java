package cl.healthmood.Health.Mood.service;

import cl.healthmood.Health.Mood.dto.CategoryRequest;
import cl.healthmood.Health.Mood.dto.CategoryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    CategoryResponse save(CategoryRequest categoryRequest);

    Optional<CategoryResponse> findById(Integer id);

    List<CategoryResponse> findAll();

    Page<CategoryResponse> findAll(Pageable pageable);

    CategoryResponse update(Integer id, CategoryRequest categoryRequest);

    void deleteById(Integer id);

    boolean existsById(Integer id);

    List<CategoryResponse> findByNameContainingIgnoreCase(String name);

    Optional<CategoryResponse> findByName(String name);

    boolean existsByName(String name);

    List<CategoryResponse> findAllWithProducts();

    long count();
}