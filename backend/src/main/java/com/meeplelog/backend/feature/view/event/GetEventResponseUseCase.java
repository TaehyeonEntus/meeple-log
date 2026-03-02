package com.meeplelog.backend.feature.view.event;

import com.meeplelog.backend.feature.event.usecase.GetEventDetailUseCase;
import com.meeplelog.backend.feature.event.usecase.GetEventGameSummariesUseCase;
import com.meeplelog.backend.feature.event.usecase.GetEventUserSummariesUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetEventResponseUseCase {
    private final GetEventDetailUseCase getEventDetailUseCase;
    private final GetEventGameSummariesUseCase getEventGameSummariesUseCase;
    private final GetEventUserSummariesUseCase getEventUserSummariesUseCase;

    public EventResponse getEventResponse(Long eventId) {
        return EventResponse.of(
                getEventDetailUseCase.getEventDetail(eventId),
                getEventGameSummariesUseCase.getEventGameSummaries(eventId),
                getEventUserSummariesUseCase.getEventUserSummaries(eventId)
        );
    }
}
