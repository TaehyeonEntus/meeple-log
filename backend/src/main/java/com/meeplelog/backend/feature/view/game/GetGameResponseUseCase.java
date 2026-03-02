package com.meeplelog.backend.feature.view.game;

import com.meeplelog.backend.feature.game.usecase.GetGameDetailUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetGameResponseUseCase {
    private final GetGameDetailUseCase getGameDetailUseCase;

    public GameResponse getGameResponse(Long gameId) {
        return GameResponse.of(getGameDetailUseCase.getGameDetail(gameId));
    }
}
