package com.meeplelog.backend.feature.category.web.dto;

public record AddCategoryRequest(
        String name,
        String description
) {
}
