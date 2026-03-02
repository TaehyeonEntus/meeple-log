package com.meeplelog.backend.feature.view.event;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/event")
@RequiredArgsConstructor
public class EventViewController {
    private final GetEventResponseUseCase getEventResponseUseCase;

    @GetMapping("/{eventId}")
    public ResponseEntity<?> getEventResponse(@PathVariable Long eventId) {
        return ResponseEntity.ok(getEventResponseUseCase.getEventResponse(eventId));
    }
}
