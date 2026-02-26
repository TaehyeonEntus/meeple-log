package com.meeplelog.backend.feature.category.dto;

import com.meeplelog.backend.domain.Category;

public record CategorySummary(
        long id,
        String name
) {
    public static CategorySummary of(Category category){
        return new CategorySummary(category.getId(), category.getName());
    }
}
