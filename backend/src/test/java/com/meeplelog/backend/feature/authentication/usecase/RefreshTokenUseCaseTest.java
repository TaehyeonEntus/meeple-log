package com.meeplelog.backend.feature.authentication.usecase;

import com.meeplelog.backend.feature.authentication.dto.Token;
import com.meeplelog.backend.security.CustomUserDetails;
import com.meeplelog.backend.security.CustomUserDetailsService;
import com.meeplelog.backend.security.JwtTokenProvider;
import com.meeplelog.backend.security.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RefreshTokenUseCaseTest {

    @Mock
    private JwtTokenProvider tokenProvider;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private CustomUserDetailsService customUserDetailsService;

    @InjectMocks
    private RefreshTokenUseCase refreshTokenUseCase;

    private String validRefreshToken;

    @BeforeEach
    void setUp() {
        validRefreshToken = "valid.refresh.token";
    }

    @Test
    @DisplayName("유효한 리프레시 토큰으로 새로운 액세스 토큰과 리프레시 토큰을 발급받는다")
    void refreshToken_success() {
        // given
        CustomUserDetails userDetails = mock(CustomUserDetails.class);
        given(jwtUtil.getIdFromJwt(validRefreshToken)).willReturn(1L);
        given(customUserDetailsService.loadUserById(1L)).willReturn(userDetails);
        given(tokenProvider.generateAccessToken(any(Authentication.class))).willReturn("new-access-token");
        given(tokenProvider.generateRefreshToken(any(Authentication.class))).willReturn("new-refresh-token");

        // when
        Token token = refreshTokenUseCase.refreshToken(validRefreshToken);

        // then
        assertThat(token).isNotNull();
        assertThat(token.accessToken()).isEqualTo("new-access-token");
        assertThat(token.refreshToken()).isEqualTo("new-refresh-token");
    }

    @Test
    @DisplayName("리프레시 토큰에서 사용자 ID를 추출한다")
    void refreshToken_extractsUserId() {
        // given
        CustomUserDetails userDetails = mock(CustomUserDetails.class);

        given(jwtUtil.getIdFromJwt(validRefreshToken)).willReturn(1L);
        given(customUserDetailsService.loadUserById(1L)).willReturn(userDetails);
        given(tokenProvider.generateAccessToken(any(Authentication.class))).willReturn("new-access-token");
        given(tokenProvider.generateRefreshToken(any(Authentication.class))).willReturn("new-refresh-token");

        // when
        refreshTokenUseCase.refreshToken(validRefreshToken);

        // then
        verify(jwtUtil).getIdFromJwt(validRefreshToken);
        verify(customUserDetailsService).loadUserById(1L);
    }

    @Test
    @DisplayName("사용자 정보를 기반으로 새로운 토큰을 생성한다")
    void refreshToken_generatesNewTokens() {
        // given
        CustomUserDetails userDetails = mock(CustomUserDetails.class);

        given(jwtUtil.getIdFromJwt(validRefreshToken)).willReturn(1L);
        given(customUserDetailsService.loadUserById(anyLong())).willReturn(userDetails);
        given(tokenProvider.generateAccessToken(any(Authentication.class))).willReturn("new-access-token");
        given(tokenProvider.generateRefreshToken(any(Authentication.class))).willReturn("new-refresh-token");

        // when
        refreshTokenUseCase.refreshToken(validRefreshToken);

        // then
        verify(tokenProvider).generateAccessToken(any(Authentication.class));
        verify(tokenProvider).generateRefreshToken(any(Authentication.class));
    }
}
