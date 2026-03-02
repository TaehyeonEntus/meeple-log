package com.meeplelog.backend.feature.view.category;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryViewController {
    private final GetCategoryResponseUseCase getCategoryResponseUseCase;

    @GetMapping("/{categoryId}")
    public ResponseEntity<?> getCategoryResponse(@PathVariable Long categoryId) {
        return ResponseEntity.ok(getCategoryResponseUseCase.getCategoryResponse(categoryId));
    }
}
