package com.meeplelog.backend.usecase.authentication;

import com.meeplelog.backend.security.CustomUserDetails;
import com.meeplelog.backend.security.CustomUserDetailsService;
import com.meeplelog.backend.security.JwtTokenProvider;
import com.meeplelog.backend.usecase.authentication.dto.RefreshTokenRequest;
import com.meeplelog.backend.usecase.authentication.dto.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenUseCase {
    private final JwtTokenProvider tokenProvider;
    private final CustomUserDetailsService customUserDetailsService;


    public TokenResponse refresh(RefreshTokenRequest request) {
        long id = tokenProvider.getIdFromJwt(request.refreshToken());

        CustomUserDetails userDetails = (CustomUserDetails) customUserDetailsService.loadUserById(id);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        String newAccessToken = tokenProvider.generateAccessToken(authentication);
        String newRefreshToken = tokenProvider.generateRefreshToken(authentication);

        return new TokenResponse(newAccessToken, newRefreshToken);
    }

}
