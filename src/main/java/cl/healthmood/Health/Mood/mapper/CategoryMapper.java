package cl.healthmood.Health.Mood.mapper;

import cl.healthmood.Health.Mood.dto.CategoryRequest;
import cl.healthmood.Health.Mood.dto.CategoryResponse;
import cl.healthmood.Health.Mood.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoryMapper {

    @Autowired
    private ProductMapper productMapper;

    public Category toEntity(CategoryRequest categoryRequest) {
        if (categoryRequest == null) {
            return null;
        }

        return Category.builder()
                .name(categoryRequest.getName())
                .description(categoryRequest.getDescription())
                .build();
    }

    public CategoryResponse toResponse(Category category) {
        if (category == null) {
            return null;
        }

        return CategoryResponse.builder()
                .categoryId(category.getCategoryId())
                .name(category.getName())
                .description(category.getDescription())
                .products(category.getProducts() != null ?
                        category.getProducts().stream()
                                .map(productMapper::toResponse)
                                .collect(Collectors.toList()) : null)
                .build();
    }

    public List<CategoryResponse> toResponseList(List<Category> categories) {
        if (categories == null) {
            return null;
        }
        return categories.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public void updateEntityFromRequest(CategoryRequest categoryRequest, Category category) {
        if (categoryRequest != null && category != null) {
            category.setName(categoryRequest.getName());
            category.setDescription(categoryRequest.getDescription());
        }
    }
}
