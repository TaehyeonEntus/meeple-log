package com.meeplelog.backend.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenProvider {
    private final SecretKey key;
    private final long accessTokenExpirationMs;
    private final long refreshTokenExpirationMs;
    private final JwtParser jwtParser;

    public JwtTokenProvider(
            @Value("${jwt.secret}") String secretKey,
            @Value("${jwt.access-token-expiration-ms}") long accessTokenExpirationMs,
            @Value("${jwt.refresh-token-expiration-ms}") long refreshTokenExpirationMs) {
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
        this.accessTokenExpirationMs = accessTokenExpirationMs;
        this.refreshTokenExpirationMs = refreshTokenExpirationMs;
        this.jwtParser = Jwts.parser().verifyWith(key).build();
    }

    public String generateAccessToken(Authentication authentication) {
        return createJwt(authentication,accessTokenExpirationMs);
    }

    public String generateRefreshToken(Authentication authentication) {
        return createJwt(authentication, refreshTokenExpirationMs);
    }

    public long getIdFromJwt(String jwt) {
        return Long.parseLong(jwtParser.parseSignedClaims(jwt).getPayload().getSubject());
    }

    private String createJwt(Authentication authentication, long expireTime){
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        Date now = new Date();
        Date expireDate = new Date(now.getTime() + expireTime);

        return Jwts.builder()
                .subject(String.valueOf(userDetails.getId()))
                .claim("name", userDetails.getName())
                .issuedAt(now)
                .expiration(expireDate)
                .signWith(key)
                .compact();
    }
}
