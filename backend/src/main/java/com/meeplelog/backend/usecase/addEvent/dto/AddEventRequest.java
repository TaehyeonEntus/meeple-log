package com.meeplelog.backend.usecase.addEvent.dto;

import java.time.Instant;
import java.util.List;

public record AddEventRequest(
        String name,
        List<AddEventGameRequest> eventGames,
        List<AddEventPlayerRequest> eventPlayers,
        Instant start,
        Instant end
) {
}

