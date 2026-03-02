package com.meeplelog.backend.feature.category.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meeplelog.backend.RestDocsConfig;
import com.meeplelog.backend.TestSecurityConfig;
import com.meeplelog.backend.domain.Category;
import com.meeplelog.backend.feature.authentication.dto.Token;
import com.meeplelog.backend.feature.category.usecase.AddCategoryUseCase;
import com.meeplelog.backend.feature.category.web.dto.AddCategoryRequest;
import com.meeplelog.backend.security.JwtAuthenticationFilter;
import com.meeplelog.backend.security.SecurityConfig;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.cookies.CookieDocumentation.cookieWithName;
import static org.springframework.restdocs.cookies.CookieDocumentation.requestCookies;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(
        value = CategoryController.class,
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
class CategoryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AddCategoryUseCase addCategoryUseCase;

    @Test
    @DisplayName("카테고리 추가 API 문서화")
    void addCategory() throws Exception {
        // given
        Token token = new Token("access-token", "refresh-token");
        AddCategoryRequest request = new AddCategoryRequest("전략 게임", "머리를 써야해요");
        Category category = Category.forTest(1L, "전략 게임", "머리를 써야해요");

        given(addCategoryUseCase.addCategory(any(AddCategoryRequest.class))).willReturn(category);

        // when
        ResultActions result = this.mockMvc.perform(
                post("/category/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .cookie(new Cookie("accessToken", token.accessToken()))
        );

        // then
        result.andExpect(status().isOk())
                .andDo(document("category-add",
                        requestFields(
                                fieldWithPath("name").description("카테고리 이름"),
                                fieldWithPath("description").description("카테고리 설명")
                        ),
                        requestCookies(
                                cookieWithName("accessToken").description("엑세스 토큰")
                        ),
                        responseFields(
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지")
                        )
                ));
    }
}