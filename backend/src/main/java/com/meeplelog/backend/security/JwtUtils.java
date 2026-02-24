package com.meeplelog.backend.security;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class JwtUtils {
    private final long accessTokenExpirationMs;
    private final long refreshTokenExpirationMs;
    private final String domain;
    public final String ACCESS_TOKEN;
    public final String REFRESH_TOKEN;

    public JwtUtils(
            @Value("${jwt.access-token-expiration-ms}") long accessTokenExpirationMs,
            @Value("${jwt.refresh-token-expiration-ms}") long refreshTokenExpirationMs,
            @Value("${jwt.domain}") String domain) {
        this.accessTokenExpirationMs = accessTokenExpirationMs;
        this.refreshTokenExpirationMs = refreshTokenExpirationMs;
        this.domain = domain;
        this.ACCESS_TOKEN = "accessToken";
        this.REFRESH_TOKEN = "refreshToken";
    }


    public void setAccessTokenOnCookie(String accessToken, HttpServletResponse response) {
        addJwtOnCookie(response, ACCESS_TOKEN, accessToken, accessTokenExpirationMs);
    }

    public void setRefreshTokenOnCookie(String refreshToken, HttpServletResponse response) {
        addJwtOnCookie(response, REFRESH_TOKEN, refreshToken, refreshTokenExpirationMs);
    }

    public void deleteAccessTokenOnCookie(HttpServletResponse response) {
        addJwtOnCookie(response, ACCESS_TOKEN, "", 0);
    }

    public void deleteRefreshTokenOnCookie(HttpServletResponse response) {
        addJwtOnCookie(response, REFRESH_TOKEN, "", 0);
    }

    private void addJwtOnCookie(HttpServletResponse response,
                                String name,
                                String value,
                                long maxAgeMs) {

        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setDomain(domain);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setMaxAge((int) (maxAgeMs / 1000));

        response.addCookie(cookie);
    }

    public String getAccessTokenFromRequest(HttpServletRequest request) {
        return getTokenFromCookies(request, ACCESS_TOKEN);
    }

    public String getRefreshTokenFromRequest(HttpServletRequest request) {
        return getTokenFromCookies(request, REFRESH_TOKEN);
    }

    private String getTokenFromCookies(HttpServletRequest request, String tokenName) {
        if (request.getCookies() == null)
            return null;

        return Arrays.stream(request.getCookies())
                .filter(cookie -> tokenName.equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElse(null);
    }
}