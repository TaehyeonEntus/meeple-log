package com.meeplelog.backend.feature.category.usecase;

import com.meeplelog.backend.feature.category.dto.CategorySummary;
import com.meeplelog.backend.service.CategoryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class GetCategorySummariesUseCaseTest {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private GetCategorySummariesUseCase getCategorySummariesUseCase;

    @Test
    @DisplayName("모든 카테고리 요약 정보를 조회한다")
    void getCategorySummaries_success() {
        // given
        List<CategorySummary> mockSummaries = List.of();
        given(categoryService.getAllCategorySummaries()).willReturn(mockSummaries);

        // when
        List<CategorySummary> result = getCategorySummariesUseCase.getCategorySummaries();

        // then
        assertThat(result).isNotNull();
        verify(categoryService).getAllCategorySummaries();
    }
}
