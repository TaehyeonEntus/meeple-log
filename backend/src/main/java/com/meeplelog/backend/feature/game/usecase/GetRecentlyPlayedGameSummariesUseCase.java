package com.meeplelog.backend.feature.game.usecase;

import com.meeplelog.backend.feature.game.dto.GameSummary;
import com.meeplelog.backend.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetRecentlyPlayedGameSummariesUseCase {
    private final GameService gameService;

    public List<GameSummary> getRecentlyPlayedGameSummaries(Long userId, Long categoryId, int limit) {
        return gameService.getRecentlyPlayedGameSummaries(userId, categoryId, limit);
    }
}
