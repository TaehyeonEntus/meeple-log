package com.meeplelog.backend.feature.view.category;

import com.meeplelog.backend.feature.category.usecase.GetCategoryDetailUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetCategoryResponseUseCase {
    private final GetCategoryDetailUseCase getCategoryDetailUseCase;

    public CategoryResponse getCategoryResponse(Long categoryId) {
        return CategoryResponse.of(getCategoryDetailUseCase.getCategoryDetail(categoryId));
    }
}
