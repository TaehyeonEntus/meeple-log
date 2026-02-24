package com.meeplelog.backend.usecase.authentication;

import com.meeplelog.backend.domain.Player;
import com.meeplelog.backend.security.CustomUserDetails;
import com.meeplelog.backend.security.CustomUserDetailsService;
import com.meeplelog.backend.security.JwtTokenProvider;
import com.meeplelog.backend.usecase.authentication.dto.RefreshTokenRequest;
import com.meeplelog.backend.usecase.authentication.dto.TokenResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RefreshTokenUseCaseTest {

    @InjectMocks
    private RefreshTokenUseCase refreshTokenUseCase;

    @Mock
    private JwtTokenProvider tokenProvider;

    @Mock
    private CustomUserDetailsService customUserDetailsService;

    @Test
    @DisplayName("토큰 재발급 성공")
    void refresh_success() {
        // given
        RefreshTokenRequest request = new RefreshTokenRequest("mockRefreshToken");
        long userId = 1L;

        Player mockPlayer = mock(Player.class);
        CustomUserDetails userDetails = new CustomUserDetails(mockPlayer);

        String newAccessToken = "newMockAccessToken";
        String newRefreshToken = "newMockRefreshToken";

        when(tokenProvider.getIdFromJwt(request.refreshToken())).thenReturn(userId);
        when(customUserDetailsService.loadUserById(userId)).thenReturn(userDetails);
        when(tokenProvider.generateAccessToken(any(Authentication.class))).thenReturn(newAccessToken);
        when(tokenProvider.generateRefreshToken(any(Authentication.class))).thenReturn(newRefreshToken);

        // when
        TokenResponse response = refreshTokenUseCase.refresh(request);

        // then
        Assertions.assertNotNull(response);
        Assertions.assertEquals(newAccessToken, response.accessToken());
        Assertions.assertEquals(newRefreshToken, response.refreshToken());
    }

    @Test
    @DisplayName("토큰 재발급 실패 - 존재하지 않는 사용자")
    void refresh_failure_userNotFound() {
        // given
        RefreshTokenRequest request = new RefreshTokenRequest("mockRefreshToken");
        long userId = 1L;

        when(tokenProvider.getIdFromJwt(request.refreshToken())).thenReturn(userId);
        when(customUserDetailsService.loadUserById(userId)).thenThrow(new UsernameNotFoundException("User not found"));

        // when & then
        Assertions.assertThrows(UsernameNotFoundException.class, () -> refreshTokenUseCase.refresh(request));
    }
}
