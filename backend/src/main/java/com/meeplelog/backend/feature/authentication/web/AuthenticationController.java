package com.meeplelog.backend.feature.authentication.web;

import com.meeplelog.backend.feature.authentication.dto.Token;
import com.meeplelog.backend.feature.authentication.usecase.LoginUseCase;
import com.meeplelog.backend.feature.authentication.usecase.LogoutUseCase;
import com.meeplelog.backend.feature.authentication.usecase.RefreshTokenUseCase;
import com.meeplelog.backend.feature.authentication.usecase.RegisterUseCase;
import com.meeplelog.backend.feature.authentication.web.dto.LoginRequest;
import com.meeplelog.backend.feature.authentication.web.dto.RegisterRequest;
import com.meeplelog.backend.security.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Token token = loginUseCase.login(request);

        ResponseCookie accessToken = jwtUtil.createAccessTokenCookie(token.accessToken());
        ResponseCookie refreshToken = jwtUtil.createRefreshTokenCookie(token.refreshToken());

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, accessToken.toString())
                .header(HttpHeaders.SET_COOKIE, refreshToken.toString())
                .body(Map.of("message", "로그인 성공"));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        logoutUseCase.logout();

        ResponseCookie accessToken = jwtUtil.deleteAccessTokenCookie();
        ResponseCookie refreshToken = jwtUtil.deleteRefreshTokenCookie();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, accessToken.toString())
                .header(HttpHeaders.SET_COOKIE, refreshToken.toString())
                .body(Map.of("message", "로그아웃 성공"));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
        Token token = refreshTokenUseCase.refreshToken(jwtUtil.getRefreshTokenFromRequest(httpRequest));

        ResponseCookie accessToken = jwtUtil.createAccessTokenCookie(token.accessToken());
        ResponseCookie refreshToken = jwtUtil.createRefreshTokenCookie(token.refreshToken());

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, accessToken.toString())
                .header(HttpHeaders.SET_COOKIE, refreshToken.toString())
                .body(Map.of("message", "토큰 재발급 성공"));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        registerUseCase.register(request);

        return ResponseEntity.ok().body(Map.of("message", "회원가입 성공"));
    }
}
