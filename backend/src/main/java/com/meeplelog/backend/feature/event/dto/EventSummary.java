package com.meeplelog.backend.feature.event.dto;

import java.time.Instant;

public record EventSummary(
        Long id,
        String name,
        Instant startTime,
        Instant endTime
) {
}
