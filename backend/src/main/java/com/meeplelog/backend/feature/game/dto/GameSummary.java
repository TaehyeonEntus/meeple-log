package com.meeplelog.backend.feature.game.dto;

import com.meeplelog.backend.domain.Game;

public record GameSummary (
        long gameId,
        String gameName,
        String gameImageUrl
){
    public static GameSummary of(Game game) {
        return new GameSummary(game.getId(), game.getName(), game.getImageUrl());
    }
}
