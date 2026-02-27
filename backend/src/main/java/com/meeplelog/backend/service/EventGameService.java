package com.meeplelog.backend.service;

import com.meeplelog.backend.domain.EventGame;
import com.meeplelog.backend.infra.repository.EventGameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventGameService {
    private final EventGameRepository eventGameRepository;

    public EventGame add(EventGame eventGame) {
        return eventGameRepository.save(eventGame);
    }
}
