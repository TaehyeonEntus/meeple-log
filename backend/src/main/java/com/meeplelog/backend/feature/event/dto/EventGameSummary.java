package com.meeplelog.backend.feature.event.dto;

import java.time.Instant;

public record EventGameSummary(
        Long id,
        String name,
        String imageUrl,
        Instant startTime,
        Instant endTime
) {
}
