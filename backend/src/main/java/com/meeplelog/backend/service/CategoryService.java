package com.meeplelog.backend.service;

import com.meeplelog.backend.domain.Category;
import com.meeplelog.backend.feature.category.dto.CategorySummary;
import com.meeplelog.backend.infra.repository.CategoryQueryRepository;
import com.meeplelog.backend.infra.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryQueryRepository categoryQueryRepository;

    public Category add(Category category){
        return categoryRepository.save(category);
    }

    public Category get(long id){
        return categoryRepository.findById(id).orElseThrow();
    }

    public List<CategorySummary> getAllCategorySummaries(){
        return categoryQueryRepository.getAllCategorySummaries();
    }
}