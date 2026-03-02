package com.meeplelog.backend.feature.category.dto;

import com.meeplelog.backend.domain.Category;

public record CategoryDetail(
        Long id,
        String name,
        String description
) {
    public static CategoryDetail of(Category category){
        return new CategoryDetail(category.getId(), category.getName(), category.getDescription());
    }
}
