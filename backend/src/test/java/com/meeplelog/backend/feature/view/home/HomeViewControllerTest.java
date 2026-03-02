package com.meeplelog.backend.feature.view.home;

import com.meeplelog.backend.RestDocsConfig;
import com.meeplelog.backend.TestSecurityConfig;
import com.meeplelog.backend.feature.authentication.dto.Token;
import com.meeplelog.backend.feature.category.dto.CategorySummary;
import com.meeplelog.backend.feature.game.dto.GameSummary;
import com.meeplelog.backend.feature.user.dto.UserDetail;
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
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.cookies.CookieDocumentation.cookieWithName;
import static org.springframework.restdocs.cookies.CookieDocumentation.requestCookies;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        value = HomeViewController.class,
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
class HomeViewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetHomeResponseUseCase getHomeResponseUseCase;

    @Test
    @DisplayName("홈 화면 정보 조회 API 문서화")
    void getHomeResponse() throws Exception {
        // given
        Token token = new Token("access-token", "refresh-token");
        UserDetail userDetail = new UserDetail(1L, "Test User", "http://image.url");
        List<CategorySummary> categories = List.of(new CategorySummary(1L, "Strategy"));
        List<GameSummary> mostPlayed = List.of(new GameSummary(1L, "Catan", "http://image.url"));
        List<GameSummary> recentlyPlayed = List.of(new GameSummary(1L, "Catan", "http://image.url"));

        HomeResponse homeResponse = new HomeResponse(userDetail, categories, mostPlayed, recentlyPlayed);
        given(getHomeResponseUseCase.getHomeResponse(any())).willReturn(homeResponse);

        // when
        ResultActions result = this.mockMvc.perform(
                get("/home")
                        .cookie(new Cookie("accessToken", token.accessToken()))
        );

        // then
        result.andExpect(status().isOk())
                .andDo(document("home-view-get",
                        requestCookies(
                                cookieWithName("accessToken").description("엑세스 토큰").optional()
                        ),
                        responseFields(
                                fieldWithPath("user.id").type(JsonFieldType.NUMBER).description("사용자 ID"),
                                fieldWithPath("user.name").type(JsonFieldType.STRING).description("사용자 이름"),
                                fieldWithPath("user.imageUrl").type(JsonFieldType.STRING).description("사용자 이미지 URL"),
                                fieldWithPath("categories[].id").type(JsonFieldType.NUMBER).description("카테고리 ID"),
                                fieldWithPath("categories[].name").type(JsonFieldType.STRING).description("카테고리 이름"),
                                fieldWithPath("mostPlayedGames[].id").type(JsonFieldType.NUMBER).description("게임 ID"),
                                fieldWithPath("mostPlayedGames[].name").type(JsonFieldType.STRING).description("게임 이름"),
                                fieldWithPath("mostPlayedGames[].imageUrl").type(JsonFieldType.STRING).description("게임 이미지 URL"),
                                fieldWithPath("recentlyPlayedGames[].id").type(JsonFieldType.NUMBER).description("게임 ID"),
                                fieldWithPath("recentlyPlayedGames[].name").type(JsonFieldType.STRING).description("게임 이름"),
                                fieldWithPath("recentlyPlayedGames[].imageUrl").type(JsonFieldType.STRING).description("게임 이미지 URL")
                        )
                ));
    }
}
