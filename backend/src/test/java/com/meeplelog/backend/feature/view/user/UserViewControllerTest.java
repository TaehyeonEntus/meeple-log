package com.meeplelog.backend.feature.view.user;

import com.meeplelog.backend.RestDocsConfig;
import com.meeplelog.backend.TestSecurityConfig;
import com.meeplelog.backend.feature.user.dto.UserDetail;
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
        value = UserViewController.class,
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
class UserViewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetUserResponseUseCase getUserResponseUseCase;

    @Test
    @DisplayName("유저 상세 정보 조회 API 문서화")
    void getUserResponse() throws Exception {
        // given
        Long userId = 1L;
        UserDetail userDetail = new UserDetail(userId, "User Name", "http://image.url");
        UserResponse userResponse = new UserResponse(userDetail);
        given(getUserResponseUseCase.getUserResponse(userId)).willReturn(userResponse);

        // when
        ResultActions result = this.mockMvc.perform(
                get("/user/{userId}", userId)
        );

        // then
        result.andExpect(status().isOk())
                .andDo(document("user-view-get",
                        responseFields(
                                fieldWithPath("userDetail.id").type(JsonFieldType.NUMBER).description("유저 ID"),
                                fieldWithPath("userDetail.name").type(JsonFieldType.STRING).description("유저 이름"),
                                fieldWithPath("userDetail.imageUrl").type(JsonFieldType.STRING).description("유저 이미지 URL")
                        )
                ));
    }
}
