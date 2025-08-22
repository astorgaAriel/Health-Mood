package cl.healthmood.Health.Mood.controller;

import cl.healthmood.Health.Mood.dto.CategoryRequest;
import cl.healthmood.Health.Mood.dto.CategoryResponse;
import cl.healthmood.Health.Mood.service.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@Validated
@Slf4j
@CrossOrigin(origins = "*")
public class CategoryController {

    private final CategoryService categoryService;

    // Solo ADMIN puede crear categorías
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryResponse> createCategory(@Valid @RequestBody CategoryRequest categoryRequest) {
        log.info("Creating new category: {}", categoryRequest.getName());
        try {
            CategoryResponse savedCategory = categoryService.save(categoryRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);
        } catch (IllegalArgumentException e) {
            log.error("Validation error creating category: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    // Tanto CUSTOMER como ADMIN pueden ver todas las categorías
    @GetMapping
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        log.info("Getting all categories");
        List<CategoryResponse> categories = categoryService.findAll();
        return ResponseEntity.ok(categories);
    }

    // Tanto CUSTOMER como ADMIN pueden ver categorías paginadas
    @GetMapping("/paginated")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
    public ResponseEntity<Page<CategoryResponse>> getAllCategoriesPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("Getting categories paginated - page: {}, size: {}", page, size);
        Pageable pageable = PageRequest.of(page, size);
        Page<CategoryResponse> categories = categoryService.findAll(pageable);
        return ResponseEntity.ok(categories);
    }

    // Tanto CUSTOMER como ADMIN pueden ver categoría por ID
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable Integer id) {
        log.info("Getting category by ID: {}", id);
        return categoryService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Solo ADMIN puede actualizar categorías
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryResponse> updateCategory(@PathVariable Integer id, @Valid @RequestBody CategoryRequest categoryRequest) {
        log.info("Updating category ID: {}", id);
        try {
            CategoryResponse updatedCategory = categoryService.update(id, categoryRequest);
            return ResponseEntity.ok(updatedCategory);
        } catch (IllegalArgumentException e) {
            log.error("Validation error updating category: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().build();
        } catch (EntityNotFoundException e) {
            log.error("Category not found with ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Unexpected error updating category with ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Solo ADMIN puede eliminar categorías
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCategory(@PathVariable Integer id) {
        log.info("Deleting category ID: {}", id);
        try {
            categoryService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalStateException e) {
            log.error("Cannot delete category with products", e);
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (EntityNotFoundException e) {
            log.error("Category not found with ID: {}", id, e);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Unexpected error deleting category with ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Tanto CUSTOMER como ADMIN pueden buscar categorías por nombre
    @GetMapping("/search")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
    public ResponseEntity<List<CategoryResponse>> searchCategoriesByName(@RequestParam String name) {
        log.info("Searching categories by name: {}", name);
        List<CategoryResponse> categories = categoryService.findByNameContainingIgnoreCase(name);
        return ResponseEntity.ok(categories);
    }

    // Tanto CUSTOMER como ADMIN pueden obtener categoría por nombre
    @GetMapping("/name/{name}")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
    public ResponseEntity<CategoryResponse> getCategoryByName(@PathVariable String name) {
        log.info("Getting category by exact name: {}", name);
        return categoryService.findByName(name)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Tanto CUSTOMER como ADMIN pueden verificar si existe por nombre
    @GetMapping("/exists/name/{name}")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
    public ResponseEntity<Boolean> existsByName(@PathVariable String name) {
        boolean exists = categoryService.existsByName(name);
        return ResponseEntity.ok(exists);
    }

    // Tanto CUSTOMER como ADMIN pueden verificar si existe por ID
    @GetMapping("/exists/{id}")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
    public ResponseEntity<Boolean> existsById(@PathVariable Integer id) {
        boolean exists = categoryService.existsById(id);
        return ResponseEntity.ok(exists);
    }

    // Tanto CUSTOMER como ADMIN pueden ver categorías con productos
    @GetMapping("/with-products")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
    public ResponseEntity<List<CategoryResponse>> getCategoriesWithProducts() {
        log.info("Getting categories with products");
        List<CategoryResponse> categories = categoryService.findAllWithProducts();
        return ResponseEntity.ok(categories);
    }

    // Tanto CUSTOMER como ADMIN pueden ver el conteo de categorías
    @GetMapping("/count")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
    public ResponseEntity<Long> getCategoryCount() {
        long count = categoryService.count();
        return ResponseEntity.ok(count);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("Validation error: {}", e.getMessage());
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> handleIllegalStateException(IllegalStateException e) {
        log.error("State error: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception e) {
        log.error("Unexpected error: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error interno del servidor");
    }
}