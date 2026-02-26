package com.meeplelog.backend.feature.authentication.web;

import com.meeplelog.backend.feature.authentication.usecase.LoginUseCase;
import com.meeplelog.backend.feature.authentication.usecase.LogoutUseCase;
import com.meeplelog.backend.feature.authentication.usecase.RefreshTokenUseCase;
import com.meeplelog.backend.feature.authentication.usecase.RegisterUseCase;
import com.meeplelog.backend.feature.authentication.web.dto.LoginRequest;
import com.meeplelog.backend.feature.authentication.web.dto.RegisterRequest;
import com.meeplelog.backend.feature.authentication.dto.Token;
import com.meeplelog.backend.security.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final JwtUtil jwtUtil;
    private final LoginUseCase loginUseCase;
    private final LogoutUseCase logoutUseCase;
    private final RefreshTokenUseCase refreshTokenUseCase;
    private final RegisterUseCase registerUseCase;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request, HttpServletResponse httpResponse){
        Token token = loginUseCase.login(request);

        jwtUtil.setAccessTokenOnCookie(token.accessToken(), httpResponse);
        jwtUtil.setRefreshTokenOnCookie(token.refreshToken(), httpResponse);

        return ResponseEntity.ok().body(Map.of("message", "로그인 성공"));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse httpResponse){
        logoutUseCase.logout();

        jwtUtil.deleteAccessTokenOnCookie(httpResponse);
        jwtUtil.deleteRefreshTokenOnCookie(httpResponse);

        return ResponseEntity.ok().body(Map.of("message", "로그아웃 성공"));
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<?> refreshToken(HttpServletRequest httpRequest, HttpServletResponse httpResponse){
        Token token = refreshTokenUseCase.refreshToken(jwtUtil.getRefreshTokenFromRequest(httpRequest));

        jwtUtil.setAccessTokenOnCookie(token.accessToken(), httpResponse);
        jwtUtil.setRefreshTokenOnCookie(token.refreshToken(), httpResponse);

        return ResponseEntity.ok().body(Map.of("message", "토큰 재발급 성공"));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request){
        registerUseCase.register(request);

        return ResponseEntity.ok().body(Map.of("message", "회원가입 성공"));
    }
}
