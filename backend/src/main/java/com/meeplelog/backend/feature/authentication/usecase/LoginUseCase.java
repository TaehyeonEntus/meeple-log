package com.meeplelog.backend.feature.authentication.usecase;

import com.meeplelog.backend.security.JwtTokenProvider;
import com.meeplelog.backend.feature.authentication.web.dto.LoginRequest;
import com.meeplelog.backend.feature.authentication.dto.Token;
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

    public Token login(LoginRequest request){
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));

        String accessToken = tokenProvider.generateAccessToken(authenticate);
        String refreshToken = tokenProvider.generateRefreshToken(authenticate);

        return new Token(accessToken, refreshToken);
    }
}
