package com.meeplelog.backend.feature.event.usecase;

import com.meeplelog.backend.feature.event.dto.EventUserSummary;
import com.meeplelog.backend.service.EventUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetEventUserSummariesUseCase {
    private final EventUserService eventUserService;

    public List<EventUserSummary> getEventUserSummaries(Long eventId) {
        return eventUserService.getEventUserSummaries(eventId);
    }
}
