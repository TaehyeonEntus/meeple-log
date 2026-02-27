package com.meeplelog.backend.service;

import com.meeplelog.backend.domain.Game;
import com.meeplelog.backend.feature.game.dto.GameSummary;
import com.meeplelog.backend.infra.repository.GameQueryRepository;
import com.meeplelog.backend.infra.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GameService {
    private final GameRepository gameRepository;
    private final GameQueryRepository gameQueryRepository;

    public Game add(Game game) {
        return gameRepository.save(game);
    }

    public Game get(long gameId) {
        return gameRepository.findById(gameId).orElseThrow();
    }

    public boolean existsByName(String name) {
        return gameRepository.existsByName(name);
    }

    public List<GameSummary> getMostPlayedGameSummaries(Long userId, Long categoryId, int limit) {
        return gameQueryRepository.getMostPlayedGameSummaries(userId, categoryId, limit);
    }

    public List<GameSummary> getRecentlyPlayedGameSummaries(Long userId, Long categoryId, int limit) {
        return gameQueryRepository.getRecentlyPlayedGameSummaries(userId, categoryId, limit);
    }
}
