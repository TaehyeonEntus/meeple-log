package com.meeplelog.backend.security;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Arrays;

@Component
public class JwtUtil {
    private final SecretKey key;
    private final long accessTokenExpirationMs;
    private final long refreshTokenExpirationMs;
    private final String domain;
    public final String ACCESS_TOKEN;
    public final String REFRESH_TOKEN;
    private final JwtParser jwtParser;


    public JwtUtil(
            @Value("${jwt.secret}") String secretKey,
            @Value("${jwt.access-token-expiration-ms}") long accessTokenExpirationMs,
            @Value("${jwt.refresh-token-expiration-ms}") long refreshTokenExpirationMs,
            @Value("${jwt.domain}") String domain) {
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
        this.accessTokenExpirationMs = accessTokenExpirationMs;
        this.refreshTokenExpirationMs = refreshTokenExpirationMs;
        this.domain = domain;
        this.ACCESS_TOKEN = "accessToken";
        this.REFRESH_TOKEN = "refreshToken";
        this.jwtParser = Jwts.parser().verifyWith(key).build();
    }


    public ResponseCookie createAccessTokenCookie(String accessToken) {
        return createCookie(ACCESS_TOKEN, accessToken, accessTokenExpirationMs);
    }

    public ResponseCookie createRefreshTokenCookie(String refreshToken) {
        return createCookie(REFRESH_TOKEN, refreshToken, accessTokenExpirationMs);
    }

    public ResponseCookie deleteAccessTokenCookie() {
        return createCookie(ACCESS_TOKEN, "", 0);
    }

    public ResponseCookie deleteRefreshTokenCookie() {
        return createCookie(REFRESH_TOKEN, "", 0);
    }

    public long getIdFromJwt(String jwt) {
        return Long.parseLong(jwtParser.parseSignedClaims(jwt).getPayload().getSubject());
    }

    private ResponseCookie createCookie(String name,
                                        String value,
                                        long maxAgeMs) {

        return ResponseCookie
                .from(name, value)
                .path("/")
                .domain(domain)
                .secure(true)
                .httpOnly(true)
                .maxAge((int) (maxAgeMs / 1000))
                .build();
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