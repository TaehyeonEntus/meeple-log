package com.meeplelog.backend.feature.event.usecase;

import com.meeplelog.backend.feature.event.dto.EventGameSummary;
import com.meeplelog.backend.service.EventGameService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetEventGameSummariesUseCase {
    private final EventGameService eventGameService;

    public List<EventGameSummary> getEventGameSummaries(Long eventId) {
        return eventGameService.getEventGameSummaries(eventId);
    }
}
