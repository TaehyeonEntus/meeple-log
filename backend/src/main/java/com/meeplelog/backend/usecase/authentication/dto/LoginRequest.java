package com.meeplelog.backend.usecase.authentication.dto;

public record LoginRequest(
        String username,
        String password
) {
}
