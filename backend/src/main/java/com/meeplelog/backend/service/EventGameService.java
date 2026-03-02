package com.meeplelog.backend.service;

import com.meeplelog.backend.domain.EventGame;
import com.meeplelog.backend.feature.event.dto.EventGameSummary;
import com.meeplelog.backend.infra.repository.EventGameQueryRepository;
import com.meeplelog.backend.infra.repository.EventGameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventGameService {
    private final EventGameRepository eventGameRepository;
    private final EventGameQueryRepository eventGameQueryRepository;

    public EventGame add(EventGame eventGame) {
        return eventGameRepository.save(eventGame);
    }

    public List<EventGameSummary> getEventGameSummaries(Long eventId) {
        return eventGameQueryRepository.getEventGameSummaries(eventId);
    }
}
