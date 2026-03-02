package com.meeplelog.backend.feature.view.event;

import com.meeplelog.backend.feature.event.dto.EventDetail;
import com.meeplelog.backend.feature.event.dto.EventGameSummary;
import com.meeplelog.backend.feature.event.dto.EventUserSummary;
import com.meeplelog.backend.feature.event.usecase.GetEventDetailUseCase;
import com.meeplelog.backend.feature.event.usecase.GetEventGameSummariesUseCase;
import com.meeplelog.backend.feature.event.usecase.GetEventUserSummariesUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class GetEventResponseUseCaseTest {

    @InjectMocks
    private GetEventResponseUseCase getEventResponseUseCase;

    @Mock
    private GetEventDetailUseCase getEventDetailUseCase;

    @Mock
    private GetEventGameSummariesUseCase getEventGameSummariesUseCase;

    @Mock
    private GetEventUserSummariesUseCase getEventUserSummariesUseCase;

    @Test
    @DisplayName("이벤트 상세 정보를 조회한다")
    void getEventResponse() {
        // given
        Long eventId = 1L;
        EventDetail eventDetail = new EventDetail(eventId, "Event Name", Instant.now(), Instant.now());
        EventGameSummary eventGameSummary = new EventGameSummary(1L, "Game Name", "http://image.url", Instant.now(), Instant.now());
        EventUserSummary eventUserSummary = new EventUserSummary(1L, "User Name", "http://image.url");

        given(getEventDetailUseCase.getEventDetail(eventId)).willReturn(eventDetail);
        given(getEventGameSummariesUseCase.getEventGameSummaries(eventId)).willReturn(Collections.singletonList(eventGameSummary));
        given(getEventUserSummariesUseCase.getEventUserSummaries(eventId)).willReturn(Collections.singletonList(eventUserSummary));

        // when
        EventResponse result = getEventResponseUseCase.getEventResponse(eventId);

        // then
        assertThat(result.eventDetail()).isEqualTo(eventDetail);
        assertThat(result.eventGameSummaries()).containsExactly(eventGameSummary);
        assertThat(result.eventUserSummaries()).containsExactly(eventUserSummary);
    }
}
