package com.meeplelog.backend.service;

import com.meeplelog.backend.domain.Event;
import com.meeplelog.backend.feature.event.dto.EventDetail;
import com.meeplelog.backend.feature.event.dto.EventSummary;
import com.meeplelog.backend.infra.repository.EventQueryRepository;
import com.meeplelog.backend.infra.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final EventQueryRepository eventQueryRepository;

    public Event add(Event event) {
        return eventRepository.save(event);
    }

    public EventDetail getEventDetail(Long eventId) {
        return eventQueryRepository.getEventDetail(eventId);
    }

    public List<EventSummary> getEventSummaries(Long userId, Long gameId) {
        return eventQueryRepository.getEventSummaries(userId, gameId);
    }
}
