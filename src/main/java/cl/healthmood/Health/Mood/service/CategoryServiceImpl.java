package cl.healthmood.Health.Mood.service;

import cl.healthmood.Health.Mood.dto.CategoryRequest;
import cl.healthmood.Health.Mood.dto.CategoryResponse;
import cl.healthmood.Health.Mood.mapper.CategoryMapper;
import cl.healthmood.Health.Mood.model.Category;
import cl.healthmood.Health.Mood.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    @Transactional
    public CategoryResponse save(CategoryRequest categoryRequest) {
        log.debug("Guardando categoría: {}", categoryRequest.getName());
        validateCategoryRequest(categoryRequest);

        if (existsByName(categoryRequest.getName())) {
            throw new IllegalArgumentException("Ya existe una categoría con el nombre: " + categoryRequest.getName());
        }

        Category category = categoryMapper.toEntity(categoryRequest);
        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.toResponse(savedCategory);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CategoryResponse> findById(Integer id) {
        log.debug("Buscando categoría por ID: {}", id);
        return categoryRepository.findById(id)
                .map(categoryMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponse> findAll() {
        log.debug("Obteniendo todas las categorías");
        return categoryRepository.findAll().stream()
                .map(categoryMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CategoryResponse> findAll(Pageable pageable) {
        log.debug("Obteniendo categorías paginadas: {}", pageable);
        return categoryRepository.findAll(pageable)
                .map(categoryMapper::toResponse);
    }

    @Override
    @Transactional
    public CategoryResponse update(Integer id, CategoryRequest categoryRequest) {
        log.debug("Actualizando categoría ID: {}", id);
        validateCategoryRequest(categoryRequest);

        return categoryRepository.findById(id)
                .map(existingCategory -> {
                    updateCategoryFields(existingCategory, categoryRequest);
                    Category updatedCategory = categoryRepository.save(existingCategory);
                    return categoryMapper.toResponse(updatedCategory);
                })
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + id));
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        log.debug("Eliminando categoría ID: {}", id);

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + id));

        if (category.getProducts() != null && !category.getProducts().isEmpty()) {
            throw new IllegalStateException("No se puede eliminar la categoría porque tiene productos asociados");
        }

        categoryRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Integer id) {
        return categoryRepository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponse> findByNameContainingIgnoreCase(String name) {
        log.debug("Buscando categorías por nombre: {}", name);
        return categoryRepository.findByNameContainingIgnoreCase(name).stream()
                .map(categoryMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CategoryResponse> findByName(String name) {
        log.debug("Buscando categoría por nombre exacto: {}", name);
        return categoryRepository.findByName(name)
                .map(categoryMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByName(String name) {
        return categoryRepository.existsByName(name);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponse> findAllWithProducts() {
        log.debug("Obteniendo categorías con productos");
        return categoryRepository.findAllWithProducts().stream()
                .map(categoryMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        return categoryRepository.count();
    }

    private void validateCategoryRequest(CategoryRequest categoryRequest) {
        if (categoryRequest.getName() == null || categoryRequest.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la categoría es obligatorio");
        }

        if (categoryRequest.getName().length() > 100) {
            throw new IllegalArgumentException("El nombre de la categoría no puede exceder 100 caracteres");
        }
    }

    private void updateCategoryFields(Category existing, CategoryRequest categoryRequest) {
        if (categoryRequest.getName() != null && !categoryRequest.getName().trim().isEmpty()) {
            // Validar que el nuevo nombre no exista si es diferente al actual
            if (!existing.getName().equals(categoryRequest.getName()) && existsByName(categoryRequest.getName())) {
                throw new IllegalArgumentException("Ya existe una categoría con el nombre: " + categoryRequest.getName());
            }
            existing.setName(categoryRequest.getName().trim());
        }

        if (categoryRequest.getDescription() != null) {
            existing.setDescription(categoryRequest.getDescription());
        }
    }
}