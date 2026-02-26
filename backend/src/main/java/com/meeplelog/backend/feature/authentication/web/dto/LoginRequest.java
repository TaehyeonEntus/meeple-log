package com.meeplelog.backend.feature.authentication.web.dto;

public record LoginRequest(
        String username,
        String password
) {
}
