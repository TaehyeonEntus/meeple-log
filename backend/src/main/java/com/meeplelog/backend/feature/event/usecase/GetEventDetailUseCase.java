package com.meeplelog.backend.feature.event.usecase;

import com.meeplelog.backend.feature.event.dto.EventDetail;
import com.meeplelog.backend.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetEventDetailUseCase {
    private final EventService eventService;

    public EventDetail getEventDetail(Long eventId) {
        return eventService.getEventDetail(eventId);
    }
}
