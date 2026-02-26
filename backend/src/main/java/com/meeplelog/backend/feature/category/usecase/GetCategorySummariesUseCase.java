package com.meeplelog.backend.feature.category.usecase;

import com.meeplelog.backend.feature.category.dto.CategorySummary;
import com.meeplelog.backend.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetCategorySummariesUseCase {
    private final CategoryService categoryService;

    public List<CategorySummary> getCategorySummaries(){
        return categoryService.getAllCategorySummaries();
    }
}
