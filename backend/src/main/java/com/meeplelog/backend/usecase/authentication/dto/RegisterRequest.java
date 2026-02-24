package com.meeplelog.backend.usecase.authentication.dto;

public record RegisterRequest(
        String username,
        String name,
        String password,
        String passwordConfirm
) {
}
