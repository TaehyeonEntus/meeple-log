package com.meeplelog.backend.usecase.authentication;

import com.meeplelog.backend.security.JwtTokenProvider;
import com.meeplelog.backend.usecase.authentication.dto.LoginRequest;
import com.meeplelog.backend.usecase.authentication.dto.TokenResponse;
import org.junit.jupiter.api.Assertions;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoginUseCaseTest {

    @InjectMocks
    private LoginUseCase loginUseCase;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenProvider tokenProvider;

    @Test
    @DisplayName("로그인 성공")
    void login_success() {
        // given
        LoginRequest request = new LoginRequest("testuser", "password123");
        Authentication authentication = mock(Authentication.class);
        String expectedAccessToken = "mockAccessToken";
        String expectedRefreshToken = "mockRefreshToken";

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(tokenProvider.generateAccessToken(authentication)).thenReturn(expectedAccessToken);
        when(tokenProvider.generateRefreshToken(authentication)).thenReturn(expectedRefreshToken);

        // when
        TokenResponse response = loginUseCase.login(request);

        // then
        Assertions.assertNotNull(response);
        Assertions.assertEquals(expectedAccessToken, response.accessToken());
        Assertions.assertEquals(expectedRefreshToken, response.refreshToken());
    }

    @Test
    @DisplayName("로그인 실패 - 잘못된 자격 증명")
    void login_failure_badCredentials() {
        // given
        LoginRequest request = new LoginRequest("wronguser", "wrongpassword");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Bad credentials"));

        // when & then
        Assertions.assertThrows(BadCredentialsException.class, () -> loginUseCase.login(request));
    }
}
