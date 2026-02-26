package com.meeplelog.backend.feature.game.usecase;

import com.meeplelog.backend.feature.game.dto.GameSummary;
import com.meeplelog.backend.service.EventGameService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetRecentlyPlayedGameSummariesUseCase {
    private final EventGameService eventGameService;

    public List<GameSummary> getRecentlyPlayedGameSummaries(int limit) {
        return eventGameService.getRecentlyPlayedGameSummaries(limit);
    }

    public List<GameSummary> getRecentlyPlayedGameSummariesByUser(long userId, int limit) {
        return eventGameService.getRecentlyPlayedGameSummariesByUser(userId, limit);
    }
}
