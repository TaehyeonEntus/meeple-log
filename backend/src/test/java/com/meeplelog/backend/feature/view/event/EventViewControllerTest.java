package com.meeplelog.backend.feature.view.event;

import com.meeplelog.backend.RestDocsConfig;
import com.meeplelog.backend.TestSecurityConfig;
import com.meeplelog.backend.feature.event.dto.EventDetail;
import com.meeplelog.backend.feature.event.dto.EventGameSummary;
import com.meeplelog.backend.feature.event.dto.EventUserSummary;
import com.meeplelog.backend.security.JwtAuthenticationFilter;
import com.meeplelog.backend.security.SecurityConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.Instant;
import java.util.Collections;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        value = EventViewController.class,
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = {
                        SecurityConfig.class,
                        JwtAuthenticationFilter.class
                }
        )
)
@AutoConfigureRestDocs
@Import(
        {
                RestDocsConfig.class,
                TestSecurityConfig.class
        }
)
class EventViewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetEventResponseUseCase getEventResponseUseCase;

    @Test
    @DisplayName("이벤트 상세 정보 조회 API 문서화")
    void getEventResponse() throws Exception {
        // given
        Long eventId = 1L;
        EventDetail eventDetail = new EventDetail(eventId, "Event Name", Instant.now(), Instant.now());
        EventGameSummary eventGameSummary = new EventGameSummary(1L, "Game Name", "http://image.url", Instant.now(), Instant.now());
        EventUserSummary eventUserSummary = new EventUserSummary(1L, "User Name", "http://image.url");
        EventResponse eventResponse = new EventResponse(eventDetail, Collections.singletonList(eventGameSummary), Collections.singletonList(eventUserSummary));
        given(getEventResponseUseCase.getEventResponse(eventId)).willReturn(eventResponse);

        // when
        ResultActions result = this.mockMvc.perform(
                get("/event/{eventId}", eventId)
        );

        // then
        result.andExpect(status().isOk())
                .andDo(document("event-view-get",
                        responseFields(
                                fieldWithPath("eventDetail.id").type(JsonFieldType.NUMBER).description("이벤트 ID"),
                                fieldWithPath("eventDetail.name").type(JsonFieldType.STRING).description("이벤트 이름"),
                                fieldWithPath("eventDetail.startTime").type(JsonFieldType.STRING).description("이벤트 시작 시간"),
                                fieldWithPath("eventDetail.endTime").type(JsonFieldType.STRING).description("이벤트 종료 시간"),
                                fieldWithPath("eventGameSummaries[].id").type(JsonFieldType.NUMBER).description("게임 ID"),
                                fieldWithPath("eventGameSummaries[].name").type(JsonFieldType.STRING).description("게임 이름"),
                                fieldWithPath("eventGameSummaries[].imageUrl").type(JsonFieldType.STRING).description("게임 이미지 URL"),
                                fieldWithPath("eventGameSummaries[].startTime").type(JsonFieldType.STRING).description("게임 시작 시간"),
                                fieldWithPath("eventGameSummaries[].endTime").type(JsonFieldType.STRING).description("게임 종료 시간"),
                                fieldWithPath("eventUserSummaries[].id").type(JsonFieldType.NUMBER).description("유저 ID"),
                                fieldWithPath("eventUserSummaries[].name").type(JsonFieldType.STRING).description("유저 이름"),
                                fieldWithPath("eventUserSummaries[].imageUrl").type(JsonFieldType.STRING).description("유저 이미지 URL")
                        )
                ));
    }
}
