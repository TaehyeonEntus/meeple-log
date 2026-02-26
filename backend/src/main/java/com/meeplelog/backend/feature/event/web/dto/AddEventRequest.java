package com.meeplelog.backend.feature.event.web.dto;

import java.time.Instant;
import java.util.List;

public record AddEventRequest(
        String name,
        List<AddEventGameRequest> eventGames,
        List<AddEventUserRequest> eventUsers,
        Instant start,
        Instant end
) {
}

