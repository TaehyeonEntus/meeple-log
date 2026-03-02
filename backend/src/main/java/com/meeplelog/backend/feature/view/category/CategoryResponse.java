package com.meeplelog.backend.feature.view.category;

import com.meeplelog.backend.feature.category.dto.CategoryDetail;

public record CategoryResponse(
        CategoryDetail categoryDetail
) {
    public static CategoryResponse of(CategoryDetail categoryDetail) {
        return new CategoryResponse(categoryDetail);
    }
}
