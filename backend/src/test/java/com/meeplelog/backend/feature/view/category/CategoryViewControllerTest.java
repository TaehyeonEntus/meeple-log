package com.meeplelog.backend.feature.view.category;

import com.meeplelog.backend.RestDocsConfig;
import com.meeplelog.backend.TestSecurityConfig;
import com.meeplelog.backend.feature.category.dto.CategoryDetail;
import com.meeplelog.backend.security.JwtAuthenticationFilter;
import com.meeplelog.backend.security.SecurityConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        value = CategoryViewController.class,
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = {
                        SecurityConfig.class,
                        JwtAuthenticationFilter.class
                }
        )
)
@AutoConfigureRestDocs
@Import(
        {
                RestDocsConfig.class,
                TestSecurityConfig.class
        }
)
class CategoryViewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetCategoryResponseUseCase getCategoryResponseUseCase;

    @Test
    @DisplayName("카테고리 상세 정보 조회 API 문서화")
    void getCategoryResponse() throws Exception {
        // given
        Long categoryId = 1L;
        CategoryDetail categoryDetail = new CategoryDetail(categoryId, "Category Name", "Category Description");
        CategoryResponse categoryResponse = new CategoryResponse(categoryDetail);
        given(getCategoryResponseUseCase.getCategoryResponse(categoryId)).willReturn(categoryResponse);

        // when
        ResultActions result = this.mockMvc.perform(
                get("/category/{categoryId}", categoryId)
        );

        // then
        result.andExpect(status().isOk())
                .andDo(document("category-view-get",
                        responseFields(
                                fieldWithPath("categoryDetail.id").type(JsonFieldType.NUMBER).description("카테고리 ID"),
                                fieldWithPath("categoryDetail.name").type(JsonFieldType.STRING).description("카테고리 이름"),
                                fieldWithPath("categoryDetail.description").type(JsonFieldType.STRING).description("카테고리 설명")
                        )
                ));
    }
}
