package com.meeplelog.backend.feature.category.usecase;

import com.meeplelog.backend.domain.Category;
import com.meeplelog.backend.feature.category.dto.CategoryDetail;
import com.meeplelog.backend.service.CategoryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class GetCategoryDetailUseCaseTest {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private GetCategoryDetailUseCase getCategoryDetailUseCase;

    @Test
    @DisplayName("카테고리 ID로 카테고리 상세 정보를 조회한다")
    void getCategoryDetail_success() {
        // given
        Category category = Category.of("전략게임", "깊은 사고가 필요한 게임");
        given(categoryService.get(1L)).willReturn(category);

        // when
        CategoryDetail result = getCategoryDetailUseCase.getCategoryDetail(1L);

        // then
        assertThat(result).isNotNull();
        verify(categoryService).get(1L);
    }

    @Test
    @DisplayName("존재하지 않는 카테고리 ID로 조회 시 예외가 발생한다")
    void getCategoryDetail_notFound_throwsException() {
        // given
        given(categoryService.get(999L)).willThrow(NoSuchElementException.class);

        // when & then
        assertThatThrownBy(() -> getCategoryDetailUseCase.getCategoryDetail(999L))
                .isInstanceOf(NoSuchElementException.class);
    }
}
