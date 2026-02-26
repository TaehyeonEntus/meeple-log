package com.meeplelog.backend.feature.game.usecase;

import com.meeplelog.backend.feature.game.dto.GameDetail;
import com.meeplelog.backend.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetGameDetailUseCase {
    private final GameService gameService;

    public GameDetail getGameDetail(long gameId){
        return GameDetail.of(gameService.get(gameId));
    }
}
