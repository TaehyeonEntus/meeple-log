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
}
