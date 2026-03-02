package com.meeplelog.backend.feature.event.dto;

import java.time.Instant;

public record EventDetail(
        Long id,
        String name,
        Instant startTime,
        Instant endTime
) {
}
