package com.meeplelog.backend.service;

import com.meeplelog.backend.domain.EventGame;
import com.meeplelog.backend.feature.game.dto.GameSummary;
import com.meeplelog.backend.infra.repository.EventGameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventGameService {
    private final EventGameRepository eventGameRepository;

    public EventGame add(EventGame eventGame) {
        return eventGameRepository.save(eventGame);
    }

    public List<GameSummary> getMostPlayedGameSummaries(int limit) {
        return eventGameRepository.getMostPlayedGameSummaries(PageRequest.of(0, limit));
    }

    public List<GameSummary> getMostPlayedGameSummariesByUser(long userId, int limit) {
        return eventGameRepository.getMostPlayedGameSummariesByUser(userId, PageRequest.of(0, limit));
    }

    public List<GameSummary> getMostPlayedGameSummariesByCategory(long categoryId, int limit) {
        return eventGameRepository.getMostPlayedGameSummariesByCategory(categoryId, PageRequest.of(0, limit));
    }



    public List<GameSummary> getRecentlyPlayedGameSummaries(int limit) {
        return eventGameRepository.getRecentlyPlayedGameSummaries(PageRequest.of(0, limit));
    }

    public List<GameSummary> getRecentlyPlayedGameSummariesByUser(long userId, int limit) {
        return eventGameRepository.getRecentlyPlayedGameSummariesByUser(userId, PageRequest.of(0, limit));
    }
}
