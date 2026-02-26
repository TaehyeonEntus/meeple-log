package com.meeplelog.backend.feature.authentication.dto;

public record Token(
        String accessToken,
        String refreshToken
) {
}
