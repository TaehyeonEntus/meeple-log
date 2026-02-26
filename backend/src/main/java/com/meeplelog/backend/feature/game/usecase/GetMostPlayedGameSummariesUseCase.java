package com.meeplelog.backend.feature.game.usecase;

import com.meeplelog.backend.feature.game.dto.GameSummary;
import com.meeplelog.backend.service.EventGameService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetMostPlayedGameSummariesUseCase {
    private final EventGameService eventGameService;

    public List<GameSummary> getMostPlayedGameSummaries(int limit) {
        return eventGameService.getMostPlayedGameSummaries(limit);
    }

    public List<GameSummary> getMostPlayedGameSummariesByUser(long userId, int limit) {
        return eventGameService.getMostPlayedGameSummariesByUser(userId, limit);
    }

    public List<GameSummary> getMostPlayedGameSummariesByCategory(long categoryId, int limit) {
        return eventGameService.getMostPlayedGameSummariesByCategory(categoryId, limit);
    }
}
