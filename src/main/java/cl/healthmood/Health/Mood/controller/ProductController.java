package cl.healthmood.Health.Mood.controller;

import cl.healthmood.Health.Mood.dto.ProductRequest;
import cl.healthmood.Health.Mood.dto.ProductResponse;
import cl.healthmood.Health.Mood.service.ProductService;
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

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
@Validated
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest productRequest) {
        log.info("Creating new product: {}", productRequest.getName());
        ProductResponse savedProduct = productService.save(productRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }

    @GetMapping("/list")
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        log.info("Getting all products");
        List<ProductResponse> products = productService.findAll();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/paginated")
    public ResponseEntity<Page<ProductResponse>> getAllProductsPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("Getting products paginated - page: {}, size: {}", page, size);
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductResponse> products = productService.findAll(pageable);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Integer id) {
        log.info("Getting product by ID: {}", id);
        return productService.findById(id)
                .map(product -> ResponseEntity.ok(product))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable Integer id,
            @Valid @RequestBody ProductRequest productRequest) {
        log.info("Updating product ID: {}", id);
        try {
            ProductResponse updatedProduct = productService.update(id, productRequest);
            return ResponseEntity.ok(updatedProduct);
        } catch (RuntimeException e) {
            log.error("Error updating product: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer id) {
        log.info("Deleting product ID: {}", id);
        try {
            productService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            log.error("Error deleting product: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductResponse>> searchProductsByName(@RequestParam String name) {
        log.info("Searching products by name: {}", name);
        List<ProductResponse> products = productService.findByNameContainingIgnoreCase(name);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductResponse>> getProductsByCategory(@PathVariable Integer categoryId) {
        log.info("Getting products by category ID: {}", categoryId);
        List<ProductResponse> products = productService.findByCategoryId(categoryId);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/price-range")
    public ResponseEntity<List<ProductResponse>> getProductsByPriceRange(
            @RequestParam Integer minPrice,
            @RequestParam Integer maxPrice) {
        log.info("Getting products by price range: {} - {}", minPrice, maxPrice);
        try {
            List<ProductResponse> products = productService.findByPriceBetween(minPrice, maxPrice);
            return ResponseEntity.ok(products);
        } catch (IllegalArgumentException e) {
            log.error("Invalid price range: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/price/min/{price}")
    public ResponseEntity<List<ProductResponse>> getProductsByMinPrice(@PathVariable Integer price) {
        log.info("Getting products with price >= {}", price);
        List<ProductResponse> products = productService.findByPriceGreaterThanEqual(price);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/price/max/{price}")
    public ResponseEntity<List<ProductResponse>> getProductsByMaxPrice(@PathVariable Integer price) {
        log.info("Getting products with price <= {}", price);
        List<ProductResponse> products = productService.findByPriceLessThanEqual(price);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> existsById(@PathVariable Integer id) {
        boolean exists = productService.existsById(id);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getProductCount() {
        long count = productService.count();
        return ResponseEntity.ok(count);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("Validation error: {}", e.getMessage());
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception e) {
        log.error("Unexpected error: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error interno del servidor");
    }
}