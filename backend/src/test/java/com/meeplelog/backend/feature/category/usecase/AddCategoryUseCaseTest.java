package com.meeplelog.backend.feature.category.usecase;

import com.meeplelog.backend.domain.Category;
import com.meeplelog.backend.feature.category.web.dto.AddCategoryRequest;
import com.meeplelog.backend.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AddCategoryUseCaseTest {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private AddCategoryUseCase addCategoryUseCase;

    private AddCategoryRequest validRequest;

    @BeforeEach
    void setUp() {
        validRequest = new AddCategoryRequest("전략게임", "깊은 사고가 필요한 게임");
    }

    @Test
    @DisplayName("정상적인 카테고리 추가 요청 시 카테고리가 생성된다")
    void addCategory_success() {
        // given
        given(categoryService.add(any(Category.class))).willAnswer(invocation -> invocation.getArgument(0));

        // when
        Category result = addCategoryUseCase.addCategory(validRequest);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("전략게임");
        assertThat(result.getDescription()).isEqualTo("깊은 사고가 필요한 게임");
        verify(categoryService).add(any(Category.class));
    }

    @Test
    @DisplayName("설명 없이 카테고리를 추가할 수 있다")
    void addCategory_withoutDescription() {
        // given
        AddCategoryRequest requestWithoutDescription = new AddCategoryRequest("파티게임", null);
        given(categoryService.add(any(Category.class))).willAnswer(invocation -> invocation.getArgument(0));

        // when
        Category result = addCategoryUseCase.addCategory(requestWithoutDescription);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("파티게임");
        assertThat(result.getDescription()).isNull();
    }

    @Test
    @DisplayName("카테고리 추가 시 서비스 레이어를 통해 저장된다")
    void addCategory_callsServiceLayer() {
        // given
        given(categoryService.add(any(Category.class))).willAnswer(invocation -> invocation.getArgument(0));

        // when
        addCategoryUseCase.addCategory(validRequest);

        // then
        verify(categoryService).add(any(Category.class));
    }

    @Test
    @DisplayName("빈 문자열로 설명을 추가할 수 있다")
    void addCategory_withEmptyDescription() {
        // given
        AddCategoryRequest requestWithEmptyDescription = new AddCategoryRequest("협력게임", "");
        given(categoryService.add(any(Category.class))).willAnswer(invocation -> invocation.getArgument(0));

        // when
        Category result = addCategoryUseCase.addCategory(requestWithEmptyDescription);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("협력게임");
        assertThat(result.getDescription()).isEmpty();
    }
}
