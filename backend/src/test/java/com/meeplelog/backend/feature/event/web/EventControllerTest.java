package com.meeplelog.backend.feature.event.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meeplelog.backend.RestDocsConfig;
import com.meeplelog.backend.TestSecurityConfig;
import com.meeplelog.backend.domain.Event;
import com.meeplelog.backend.feature.authentication.dto.Token;
import com.meeplelog.backend.feature.event.usecase.AddEventUseCase;
import com.meeplelog.backend.feature.event.web.dto.AddEventGameRequest;
import com.meeplelog.backend.feature.event.web.dto.AddEventRequest;
import com.meeplelog.backend.feature.event.web.dto.AddEventUserRequest;
import com.meeplelog.backend.security.JwtAuthenticationFilter;
import com.meeplelog.backend.security.SecurityConfig;
import com.meeplelog.backend.security.SecurityUtil;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.Instant;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.cookies.CookieDocumentation.cookieWithName;
import static org.springframework.restdocs.cookies.CookieDocumentation.requestCookies;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        value = EventController.class,
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
class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AddEventUseCase addEventUseCase;

    @MockBean
    private SecurityUtil securityUtil;

    @Test
    @DisplayName("이벤트 추가 API 문서화")
    void addEvent() throws Exception {
        Token token = new Token("access-token", "refresh-token");
        AddEventRequest request = new AddEventRequest("name", List.of(new AddEventGameRequest(1L, Instant.now(), Instant.now())), List.of(new AddEventUserRequest(1L)), Instant.now(), Instant.now());
        Event event = Event.forTest(1L, "name", Instant.now(), Instant.now());

        given(securityUtil.getIdFromAuthentication(any())).willReturn(1L);
        given(addEventUseCase.addEvent(any(AddEventRequest.class))).willReturn(event);

        // when
        ResultActions result = this.mockMvc.perform(
                post("/event/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .cookie(new Cookie("accessToken", token.accessToken()))
        );

        // then
        result.andExpect(status().isOk())
                .andDo(document("event-add",
                        requestFields(
                                fieldWithPath("name").type(JsonFieldType.STRING).description("이벤트 이름"),
                                fieldWithPath("eventGames").type(JsonFieldType.ARRAY).description("이벤트 게임 목록"),
                                fieldWithPath("eventGames[].id").type(JsonFieldType.NUMBER).description("이벤트 게임 id"),
                                fieldWithPath("eventGames[].startTime").type(JsonFieldType.STRING).description("이벤트 게임 시작 시간"),
                                fieldWithPath("eventGames[].endTime").type(JsonFieldType.STRING).description("이벤트 게임 종료 시간"),
                                fieldWithPath("eventUsers").type(JsonFieldType.ARRAY).description("이벤트 참여자 목록"),
                                fieldWithPath("eventUsers[].id").type(JsonFieldType.NUMBER).description("이벤트 참여자 id"),
                                fieldWithPath("startTime").type(JsonFieldType.STRING).description("이벤트 시작 시간"),
                                fieldWithPath("endTime").type(JsonFieldType.STRING).description("이벤트 종료 시간")
                        ),
                        requestCookies(
                                cookieWithName("accessToken").description("엑세스 토큰")
                        ),
                        responseFields(
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지")
                        )
                ));
    }
}
