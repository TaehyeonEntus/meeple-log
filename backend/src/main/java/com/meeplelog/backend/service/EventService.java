package com.meeplelog.backend.service;

import com.meeplelog.backend.domain.Event;
import com.meeplelog.backend.infra.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;

    public Event add(Event event){
        return eventRepository.save(event);
    }
}
