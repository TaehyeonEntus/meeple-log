package com.meeplelog.backend.feature.category.web;

import com.meeplelog.backend.feature.category.usecase.AddCategoryUseCase;
import com.meeplelog.backend.feature.category.web.dto.AddCategoryRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {
    private final AddCategoryUseCase addCategoryUseCase;

    @PostMapping("/add")
    public ResponseEntity<?> addCategory(@RequestBody AddCategoryRequest request) {
        addCategoryUseCase.addCategory(request);

        return ResponseEntity.ok().body(Map.of("message", "카테고리 추가 완료"));
    }
}
