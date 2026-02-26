package com.meeplelog.backend.feature.event.web.dto;

import java.time.Instant;

public record AddEventGameRequest(
        long gameId,
        Instant start,
        Instant end
) {
}
