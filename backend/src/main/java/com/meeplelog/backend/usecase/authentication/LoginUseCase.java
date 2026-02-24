package com.meeplelog.backend.usecase.authentication;

import com.meeplelog.backend.security.JwtTokenProvider;
import com.meeplelog.backend.usecase.authentication.dto.LoginRequest;
import com.meeplelog.backend.usecase.authentication.dto.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginUseCase {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;

    public TokenResponse login(LoginRequest request){
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));

        String accessToken = tokenProvider.generateAccessToken(authenticate);
        String refreshToken = tokenProvider.generateRefreshToken(authenticate);

        return new TokenResponse(accessToken, refreshToken);
    }
}
