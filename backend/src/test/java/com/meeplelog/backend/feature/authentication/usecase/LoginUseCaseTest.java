package com.meeplelog.backend.feature.authentication.usecase;

import com.meeplelog.backend.feature.authentication.dto.Token;
import com.meeplelog.backend.feature.authentication.web.dto.LoginRequest;
import com.meeplelog.backend.security.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class LoginUseCaseTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenProvider tokenProvider;

    @InjectMocks
    private LoginUseCase loginUseCase;

    private LoginRequest loginRequest;

    @BeforeEach
    void setUp() {
        loginRequest = new LoginRequest("testUser", "password123");
    }

    @Test
    @DisplayName("정상적인 로그인 요청 시 액세스 토큰과 리프레시 토큰이 발급된다")
    void login_success() {
        // given
        Authentication authentication = mock(Authentication.class);
        given(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .willReturn(authentication);
        given(tokenProvider.generateAccessToken(authentication)).willReturn("access-token-123");
        given(tokenProvider.generateRefreshToken(authentication)).willReturn("refresh-token-456");

        // when
        Token token = loginUseCase.login(loginRequest);

        // then
        assertThat(token).isNotNull();
        assertThat(token.accessToken()).isEqualTo("access-token-123");
        assertThat(token.refreshToken()).isEqualTo("refresh-token-456");
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(tokenProvider).generateAccessToken(authentication);
        verify(tokenProvider).generateRefreshToken(authentication);
    }

    @Test
    @DisplayName("잘못된 인증 정보로 로그인 시 BadCredentialsException이 발생한다")
    void login_invalidCredentials_throwsException() {
        // given
        given(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .willThrow(new BadCredentialsException("Invalid credentials"));

        // when & then
        assertThatThrownBy(() -> loginUseCase.login(loginRequest))
                .isInstanceOf(BadCredentialsException.class)
                .hasMessage("Invalid credentials");
    }

    @Test
    @DisplayName("로그인 시 올바른 사용자명과 비밀번호로 인증을 시도한다")
    void login_authenticatesWithCorrectCredentials() {
        // given
        Authentication authentication = mock(Authentication.class);
        given(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .willReturn(authentication);
        given(tokenProvider.generateAccessToken(authentication)).willReturn("access-token");
        given(tokenProvider.generateRefreshToken(authentication)).willReturn("refresh-token");

        // when
        loginUseCase.login(loginRequest);

        // then
        verify(authenticationManager).authenticate(
                any(UsernamePasswordAuthenticationToken.class)
        );
    }
}
