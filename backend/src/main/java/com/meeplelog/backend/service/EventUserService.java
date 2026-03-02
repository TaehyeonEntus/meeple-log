package com.meeplelog.backend.service;

import com.meeplelog.backend.domain.EventUser;
import com.meeplelog.backend.feature.event.dto.EventUserSummary;
import com.meeplelog.backend.infra.repository.EventUserQueryRepository;
import com.meeplelog.backend.infra.repository.EventUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventUserService {
    private final EventUserRepository eventUserRepository;
    private final EventUserQueryRepository eventUserQueryRepository;

    public EventUser add(EventUser eventUser) {
        return eventUserRepository.save(eventUser);
    }

    public List<EventUserSummary> getEventUserSummaries(Long eventId) {
        return eventUserQueryRepository.getEventUserSummaries(eventId);
    }
}
