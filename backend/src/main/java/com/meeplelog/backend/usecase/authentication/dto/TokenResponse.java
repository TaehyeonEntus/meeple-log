package com.meeplelog.backend.usecase.authentication.dto;

public record TokenResponse(
        String accessToken,
        String refreshToken
) {
}
