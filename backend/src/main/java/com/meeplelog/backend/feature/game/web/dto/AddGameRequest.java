package com.meeplelog.backend.feature.game.web.dto;

public record AddGameRequest(
        String name,
        String imageUrl
) {
}
