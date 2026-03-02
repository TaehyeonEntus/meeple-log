package com.meeplelog.backend.feature.view.game;

import com.meeplelog.backend.feature.game.dto.GameDetail;

public record GameResponse(
        GameDetail gameDetail
) {
    public static GameResponse of(GameDetail gameDetail) {
        return new GameResponse(gameDetail);
    }
}
