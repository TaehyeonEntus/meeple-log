package com.meeplelog.backend.feature.authentication.web.dto;

public record RegisterRequest(
        String username,
        String name,
        String password,
        String passwordConfirm
) {
}
