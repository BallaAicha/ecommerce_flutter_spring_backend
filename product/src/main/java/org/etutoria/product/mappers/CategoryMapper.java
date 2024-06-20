package org.etutoria.product.mappers;

import org.etutoria.product.dto.CategoryResponse;
import org.etutoria.product.dto.ProductResponse;
import org.etutoria.product.entities.Category;
import org.springframework.stereotype.Service;

@Service
public class CategoryMapper {
    public CategoryResponse toCategory(Category category) {
        return new CategoryResponse(
                category.getId(),
                category.getName(),
                category.getDescription()
        );
    }
}
