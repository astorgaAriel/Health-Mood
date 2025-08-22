package cl.healthmood.Health.Mood.service;

import cl.healthmood.Health.Mood.dto.ProductRequest;
import cl.healthmood.Health.Mood.dto.ProductResponse;
import cl.healthmood.Health.Mood.mapper.ProductMapper;
import cl.healthmood.Health.Mood.model.Product;
import cl.healthmood.Health.Mood.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    @Transactional
    public ProductResponse save(ProductRequest productRequest) {
        log.debug("Guardando producto: {}", productRequest.getName());
        validateProductRequest(productRequest);

        Product product = productMapper.toEntity(productRequest);
        Product savedProduct = productRepository.save(product);

        return productMapper.toResponse(savedProduct);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProductResponse> findById(Integer id) {
        log.debug("Buscando producto por ID: {}", id);
        return productRepository.findById(id)
                .map(productMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> findAll() {
        log.debug("Obteniendo todos los productos");
        List<Product> products = productRepository.findAll();
        return productMapper.toResponseList(products);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponse> findAll(Pageable pageable) {
        log.debug("Obteniendo productos paginados: {}", pageable);
        Page<Product> productPage = productRepository.findAll(pageable);
        return productPage.map(productMapper::toResponse);
    }

    @Override
    @Transactional
    public ProductResponse update(Integer id, ProductRequest productRequest) {
        log.debug("Actualizando producto ID: {}", id);

        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));

        productMapper.updateEntityFromRequest(existingProduct, productRequest);
        Product updatedProduct = productRepository.save(existingProduct);

        return productMapper.toResponse(updatedProduct);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        log.debug("Eliminando producto ID: {}", id);

        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Producto no encontrado con ID: " + id);
        }

        productRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Integer id) {
        return productRepository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> findByNameContainingIgnoreCase(String name) {
        log.debug("Buscando productos por nombre: {}", name);
        List<Product> products = productRepository.findByNameContainingIgnoreCase(name);
        return productMapper.toResponseList(products);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> findByCategoryId(Integer categoryId) {
        log.debug("Buscando productos por categoría ID: {}", categoryId);
        List<Product> products = productRepository.findByCategoryCategoryId(categoryId);
        return productMapper.toResponseList(products);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> findByPriceBetween(Integer minPrice, Integer maxPrice) {
        log.debug("Buscando productos en rango de precios: {} - {}", minPrice, maxPrice);
        validatePriceRange(minPrice, maxPrice);
        List<Product> products = productRepository.findByPriceBetween(minPrice, maxPrice);
        return productMapper.toResponseList(products);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> findByPriceGreaterThanEqual(Integer price) {
        log.debug("Buscando productos con precio >= {}", price);
        List<Product> products = productRepository.findByPriceGreaterThanEqual(price);
        return productMapper.toResponseList(products);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> findByPriceLessThanEqual(Integer price) {
        log.debug("Buscando productos con precio <= {}", price);
        List<Product> products = productRepository.findByPriceLessThanEqual(price);
        return productMapper.toResponseList(products);
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        return productRepository.count();
    }

    private void validateProductRequest(ProductRequest productRequest) {
        if (productRequest.getName() == null || productRequest.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del producto es obligatorio");
        }
        if (productRequest.getPrice() == null || productRequest.getPrice() < 0) {
            throw new IllegalArgumentException("El precio debe ser mayor o igual a 0");
        }
    }

    private void validatePriceRange(Integer minPrice, Integer maxPrice) {
        if (minPrice == null || maxPrice == null) {
            throw new IllegalArgumentException("Los precios mínimo y máximo son obligatorios");
        }
        if (minPrice < 0 || maxPrice < 0) {
            throw new IllegalArgumentException("Los precios no pueden ser negativos");
        }
        if (minPrice > maxPrice) {
            throw new IllegalArgumentException("El precio mínimo no puede ser mayor al máximo");
        }
    }
}