package com.meeplelog.backend.feature.category.usecase;

import com.meeplelog.backend.feature.category.dto.CategoryDetail;
import com.meeplelog.backend.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetCategoryDetailUseCase {
    private final CategoryService categoryService;

    public CategoryDetail getCategoryDetail(long categoryId){
        return CategoryDetail.of(categoryService.get(categoryId));
    }
}
