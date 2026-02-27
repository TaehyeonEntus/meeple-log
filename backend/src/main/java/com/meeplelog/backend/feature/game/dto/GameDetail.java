package com.meeplelog.backend.feature.game.dto;

import com.meeplelog.backend.domain.Game;

public record GameDetail(
        Long gameId,
        String gameName,
        String gameImageUrl
) {
    public static GameDetail of(Game game){
        return new GameDetail(game.getId(), game.getName(), game.getImageUrl());
    }
}
