package com.meeplelog.backend.usecase.addEvent.dto;

import java.time.Instant;

public record AddEventGameRequest(
        long gameId,
        Instant start,
        Instant end
) {
}
