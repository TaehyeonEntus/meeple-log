package com.meeplelog.backend.feature.category.dto;

import com.meeplelog.backend.domain.Category;

public record CategoryDetail(
        Long categoryId,
        String categoryName,
        String categoryDescription
) {
    public static CategoryDetail of(Category category){
        return new CategoryDetail(category.getId(), category.getName(), category.getDescription());
    }
}
