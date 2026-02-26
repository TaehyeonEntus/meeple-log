package com.meeplelog.backend.feature.authentication.usecase;

import com.meeplelog.backend.security.CustomUserDetails;
import com.meeplelog.backend.security.CustomUserDetailsService;
import com.meeplelog.backend.security.JwtTokenProvider;
import com.meeplelog.backend.feature.authentication.dto.Token;
import com.meeplelog.backend.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenUseCase {
    private final JwtTokenProvider tokenProvider;
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;


    public Token refreshToken(String refreshToken) {
        long id = jwtUtil.getIdFromJwt(refreshToken);

        CustomUserDetails userDetails = (CustomUserDetails) customUserDetailsService.loadUserById(id);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        String newAccessToken = tokenProvider.generateAccessToken(authentication);
        String newRefreshToken = tokenProvider.generateRefreshToken(authentication);

        return new Token(newAccessToken, newRefreshToken);
    }

}
