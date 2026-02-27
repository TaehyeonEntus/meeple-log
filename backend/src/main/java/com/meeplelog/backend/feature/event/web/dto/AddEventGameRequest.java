package com.meeplelog.backend.feature.event.web.dto;

import java.time.Instant;

public record AddEventGameRequest(
        Long gameId,
        Instant start,
        Instant end
) {
}
