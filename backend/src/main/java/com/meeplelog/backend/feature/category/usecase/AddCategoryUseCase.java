package com.meeplelog.backend.feature.category.usecase;

import com.meeplelog.backend.domain.Category;
import com.meeplelog.backend.feature.category.web.dto.AddCategoryRequest;
import com.meeplelog.backend.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddCategoryUseCase {
    private final CategoryService categoryService;

    public Category addCategory(AddCategoryRequest request){
        return categoryService.add(Category.of(request.name(), request.description()));
    }
}
