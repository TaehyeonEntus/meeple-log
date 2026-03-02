package com.meeplelog.backend.feature.authentication.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meeplelog.backend.RestDocsConfig;
import com.meeplelog.backend.TestSecurityConfig;
import com.meeplelog.backend.domain.User;
import com.meeplelog.backend.feature.authentication.dto.Token;
import com.meeplelog.backend.feature.authentication.usecase.LoginUseCase;
import com.meeplelog.backend.feature.authentication.usecase.LogoutUseCase;
import com.meeplelog.backend.feature.authentication.usecase.RefreshTokenUseCase;
import com.meeplelog.backend.feature.authentication.usecase.RegisterUseCase;
import com.meeplelog.backend.feature.authentication.web.dto.LoginRequest;
import com.meeplelog.backend.feature.authentication.web.dto.RegisterRequest;
import com.meeplelog.backend.security.JwtAuthenticationFilter;
import com.meeplelog.backend.security.JwtUtil;
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
import org.springframework.http.ResponseCookie;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.cookies.CookieDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        value = AuthenticationController.class,
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
class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private LoginUseCase loginUseCase;

    @MockBean
    private LogoutUseCase logoutUseCase;

    @MockBean
    private RefreshTokenUseCase refreshTokenUseCase;

    @MockBean
    private RegisterUseCase registerUseCase;

    @MockBean
    private JwtUtil jwtUtil;

    @Test
    @DisplayName("로그인 API 문서화")
    void login() throws Exception {
        // given
        LoginRequest loginRequest = new LoginRequest("testUser", "password123");
        Token token = new Token("valid-access-token", "valid-refresh-token");

        given(loginUseCase.login(any(LoginRequest.class))).willReturn(token);

        given(jwtUtil.createAccessTokenCookie(any()))
                .willReturn(ResponseCookie.from("accessToken", "valid-access-token")
                        .path("/")
                        .httpOnly(true)
                        .build());

        given(jwtUtil.createRefreshTokenCookie(any()))
                .willReturn(ResponseCookie.from("refreshToken", "valid-refresh-token")
                        .path("/")
                        .httpOnly(true)
                        .build());
        // when
        ResultActions result = this.mockMvc.perform(
                post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest))
        );

        // then
        result.andExpect(status().isOk())
                .andDo(document("auth-login",
                        requestFields(
                                fieldWithPath("username").type(JsonFieldType.STRING).description("사용자 아이디"),
                                fieldWithPath("password").type(JsonFieldType.STRING).description("사용자 비밀번호")
                        ),
                        responseFields(
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지")
                        ),
                        responseCookies(
                                cookieWithName("accessToken").description("액세스 토큰"),
                                cookieWithName("refreshToken").description("리프레시 토큰")
                        )
                ));
    }

    @Test
    @DisplayName("로그아웃 API 문서화")
    void logout() throws Exception {
        // given
        given(jwtUtil.deleteRefreshTokenCookie())
                .willReturn(ResponseCookie.from("accessToken", "valid-access-token")
                        .path("/")
                        .httpOnly(true)
                        .maxAge(0)
                        .build());

        given(jwtUtil.deleteAccessTokenCookie())
                .willReturn(ResponseCookie.from("refreshToken", "valid-refresh-token")
                        .path("/")
                        .httpOnly(true)
                        .maxAge(0)
                        .build());

        // when
        ResultActions result = this.mockMvc.perform(
                post("/auth/logout")
                        .cookie(new Cookie("accessToken", "validToken"))
        );

        // then
        result.andExpect(status().isOk())
                .andDo(document("auth-logout",
                        requestCookies(
                                cookieWithName("accessToken").description("엑세스 토큰")
                        ),
                        responseFields(
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지")
                        ),
                        responseCookies(
                                cookieWithName("accessToken").description("만료된 액세스 토큰"),
                                cookieWithName("refreshToken").description("만료된 리프레시 토큰")
                        )
                ));
    }

    @Test
    @DisplayName("리프레시 토큰 API 문서화")
    void refreshToken() throws Exception {
        // given
        String refreshToken = "old-refresh-token";
        Token token = new Token("new-access-token", "new-refresh-token");

        given(jwtUtil.getRefreshTokenFromRequest(any())).willReturn(refreshToken);
        given(refreshTokenUseCase.refreshToken(refreshToken)).willReturn(token);

        given(jwtUtil.createAccessTokenCookie(any()))
                .willReturn(ResponseCookie.from("accessToken", "valid-access-token")
                        .path("/")
                        .httpOnly(true)
                        .build());

        given(jwtUtil.createRefreshTokenCookie(any()))
                .willReturn(ResponseCookie.from("refreshToken", "valid-refresh-token")
                        .path("/")
                        .httpOnly(true)
                        .build());

        // when
        ResultActions result = this.mockMvc.perform(
                post("/auth/refresh")
                        .cookie(new Cookie("refreshToken", refreshToken))
        );

        // then
        result.andExpect(status().isOk())
                .andDo(document("auth-refresh",
                        requestCookies(
                                cookieWithName("refreshToken").description("리프레시 토큰")
                        ),
                        responseFields(
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지")
                        ),
                        responseCookies(
                                cookieWithName("accessToken").description("액세스 토큰"),
                                cookieWithName("refreshToken").description("리프레시 토큰")
                        )
                ));
    }

    @Test
    @DisplayName("회원가입 API 문서화")
    void register() throws Exception {
        // given
        RegisterRequest registerRequest = new RegisterRequest("testUser", "테스트유저", "password", "password");
        User user = User.of("테스트유저", "testUser", "encoded-password", null);


        given(registerUseCase.register(any(RegisterRequest.class))).willReturn(user);

        // when
        ResultActions result = this.mockMvc.perform(
                post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequest))
        );

        // then
        result.andExpect(status().isOk())
                .andDo(document("auth-register",
                        requestFields(
                                fieldWithPath("username").type(JsonFieldType.STRING).description("사용자 아이디"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("사용자 이름"),
                                fieldWithPath("password").type(JsonFieldType.STRING).description("사용자 비밀번호"),
                                fieldWithPath("passwordConfirm").type(JsonFieldType.STRING).description("사용자 비밀번호 확인")
                        ),
                        responseFields(
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지")
                        )
                ));
    }
}
