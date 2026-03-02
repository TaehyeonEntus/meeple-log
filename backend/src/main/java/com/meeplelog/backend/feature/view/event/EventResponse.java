package com.meeplelog.backend.feature.view.event;

import com.meeplelog.backend.feature.event.dto.EventDetail;
import com.meeplelog.backend.feature.event.dto.EventGameSummary;
import com.meeplelog.backend.feature.event.dto.EventUserSummary;

import java.util.List;

public record EventResponse(
        EventDetail eventDetail,
        List<EventGameSummary> eventGameSummaries,
        List<EventUserSummary> eventUserSummaries
) {
    public static EventResponse of(EventDetail eventDetail, List<EventGameSummary> eventGameSummaries, List<EventUserSummary> eventUserSummaries) {
        return new EventResponse(eventDetail, eventGameSummaries, eventUserSummaries);
    }
}
