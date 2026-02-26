package com.meeplelog.backend.feature.event.usecase;

import com.meeplelog.backend.domain.Event;
import com.meeplelog.backend.domain.EventGame;
import com.meeplelog.backend.domain.EventUser;
import com.meeplelog.backend.service.EventService;
import com.meeplelog.backend.service.GameService;
import com.meeplelog.backend.service.UserService;
import com.meeplelog.backend.feature.event.web.dto.AddEventRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AddEventUseCase {
    private final EventService eventService;
    private final UserService userService;
    private final GameService gameService;

    @Transactional
    public Event addEvent(AddEventRequest request) {
        String name = request.name();

        Instant start = request.start();
        Instant end = request.end();

        Event event = Event.of(name, start, end);

        List<EventGame> eventGames = request.eventGames().stream()
                .map(dto ->
                        EventGame.of(
                                dto.start(),
                                dto.end(),
                                event,
                                gameService.get(dto.gameId())
                        )
                ).toList();

        List<EventUser> eventUsers = request.eventUsers().stream()
                .map(dto ->
                        EventUser.of(
                                event,
                                userService.get(dto.userId())
                        )
                ).toList();

        event.getEventGames().addAll(eventGames);
        event.getEventUsers().addAll(eventUsers);

        return eventService.add(event);
    }
}
