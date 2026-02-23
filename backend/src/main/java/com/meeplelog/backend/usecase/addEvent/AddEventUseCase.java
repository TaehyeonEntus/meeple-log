package com.meeplelog.backend.usecase.addEvent;

import com.meeplelog.backend.domain.Event;
import com.meeplelog.backend.domain.EventGame;
import com.meeplelog.backend.domain.EventPlayer;
import com.meeplelog.backend.service.EventService;
import com.meeplelog.backend.service.GameService;
import com.meeplelog.backend.service.PlayerService;
import com.meeplelog.backend.usecase.addEvent.dto.AddEventRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AddEventUseCase {
    private final EventService eventService;
    private final PlayerService playerService;
    private final GameService gameService;

    @Transactional
    public void addEvent(AddEventRequest request) {
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

        List<EventPlayer> eventPlayers = request.eventPlayers().stream()
                .map(dto ->
                        EventPlayer.of(
                                event,
                                playerService.get(dto.playerId())
                        )
                ).toList();

        event.getEventGames().addAll(eventGames);
        event.getEventPlayers().addAll(eventPlayers);

        eventService.add(event);
    }
}
