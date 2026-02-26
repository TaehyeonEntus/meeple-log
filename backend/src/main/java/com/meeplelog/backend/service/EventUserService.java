package com.meeplelog.backend.service;

import com.meeplelog.backend.domain.EventUser;
import com.meeplelog.backend.infra.repository.EventUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventUserService {
    private final EventUserRepository eventUserRepository;

    public EventUser add(EventUser eventUser) {
        return eventUserRepository.save(eventUser);
    }
}
