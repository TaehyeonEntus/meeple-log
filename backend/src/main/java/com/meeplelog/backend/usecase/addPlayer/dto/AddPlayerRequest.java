package com.meeplelog.backend.usecase.addPlayer.dto;

public record AddPlayerRequest(
        String username,
        String name,
        String password,
        String passwordConfirm
) {
}
