package com.meeplelog.backend.feature.category.web;

import com.meeplelog.backend.feature.category.usecase.AddCategoryUseCase;
import com.meeplelog.backend.feature.category.usecase.GetCategoryDetailUseCase;
import com.meeplelog.backend.feature.category.web.dto.AddCategoryRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {
    private final AddCategoryUseCase addCategoryUseCase;
    private final GetCategoryDetailUseCase getCategoryDetailUseCase;

    @PostMapping("/add")
    public ResponseEntity<?> addCategory(@RequestBody AddCategoryRequest request){
        addCategoryUseCase.addCategory(request);

        return ResponseEntity.ok().body(Map.of("message", "카테고리 추가 완료"));
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<?> getCategoryDetail(@PathVariable long categoryId){
        return ResponseEntity.ok(getCategoryDetailUseCase.getCategoryDetail(categoryId));
    }
}
