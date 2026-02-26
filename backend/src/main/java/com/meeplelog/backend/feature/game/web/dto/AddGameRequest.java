package com.meeplelog.backend.feature.game.web.dto;

public record AddGameRequest(
        String gameName,
        String imageUrl
) {
}
