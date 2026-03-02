package com.meeplelog.backend.feature.event.web.dto;

import java.time.Instant;

public record AddEventGameRequest(
        Long id,
        Instant startTime,
        Instant endTime
) {
}
