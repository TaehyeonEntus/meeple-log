package com.meeplelog.backend.feature.event.usecase;

import com.meeplelog.backend.domain.Event;
import com.meeplelog.backend.domain.Game;
import com.meeplelog.backend.domain.User;
import com.meeplelog.backend.exception.EventGameRequiredException;
import com.meeplelog.backend.exception.EventUserRequiredException;
import com.meeplelog.backend.feature.event.web.dto.AddEventGameRequest;
import com.meeplelog.backend.feature.event.web.dto.AddEventRequest;
import com.meeplelog.backend.feature.event.web.dto.AddEventUserRequest;
import com.meeplelog.backend.service.EventService;
import com.meeplelog.backend.service.GameService;
import com.meeplelog.backend.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AddEventUseCaseTest {

    @Mock
    private EventService eventService;

    @Mock
    private UserService userService;

    @Mock
    private GameService gameService;

    @InjectMocks
    private AddEventUseCase addEventUseCase;

    private AddEventRequest addEventRequest;
    private Game mockGame;
    private User mockUser;
    private Instant eventStart;
    private Instant eventEnd;
    private Instant gameStart;
    private Instant gameEnd;

    @BeforeEach
    void setUp() {
        eventStart = Instant.parse("2024-01-01T10:00:00Z");
        eventEnd = Instant.parse("2024-01-01T18:00:00Z");
        gameStart = Instant.parse("2024-01-01T10:00:00Z");
        gameEnd = Instant.parse("2024-01-01T12:00:00Z");

        mockGame = Game.of("테스트게임", "http://image.url");
        mockUser = User.of("테스트유저", "testUser", "password", null);

        AddEventGameRequest addEventGameRequest = new AddEventGameRequest(1L, gameStart, gameEnd);
        AddEventUserRequest addEventUserRequest = new AddEventUserRequest(1L);

        addEventRequest = new AddEventRequest(
                "보드게임 모임",
                List.of(addEventGameRequest),
                List.of(addEventUserRequest),
                eventStart,
                eventEnd
        );
    }

    @Test
    @DisplayName("이벤트 추가 시 게임과 유저 정보가 포함되어 저장된다")
    void addEvent_success() {
        // given
        given(gameService.get(1L)).willReturn(mockGame);
        given(userService.get(1L)).willReturn(mockUser);
        given(eventService.add(any(Event.class))).willAnswer(invocation -> invocation.getArgument(0));

        // when
        Event result = addEventUseCase.addEvent(addEventRequest);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("보드게임 모임");
        assertThat(result.getStart()).isEqualTo(eventStart);
        assertThat(result.getEnd()).isEqualTo(eventEnd);
        assertThat(result.getEventGames()).hasSize(1);
        assertThat(result.getEventUsers()).hasSize(1);
        verify(eventService).add(any(Event.class));
    }

    @Test
    @DisplayName("여러 게임과 유저가 포함된 이벤트를 생성할 수 있다")
    void addEvent_multipleGamesAndUsers() {
        // given
        Game game2 = Game.of("게임2", "http://image2.url");
        User user2 = User.of("유저2", "user2", "password", null);

        AddEventGameRequest addEventGameRequest1 = new AddEventGameRequest(1L, gameStart, gameEnd);
        AddEventGameRequest addEventGameRequest2 = new AddEventGameRequest(2L, gameStart.plusSeconds(1), gameEnd.plusSeconds(1));

        AddEventUserRequest addEventUserRequest1 = new AddEventUserRequest(1L);
        AddEventUserRequest addEventUserRequest2 = new AddEventUserRequest(2L);

        AddEventRequest multiRequest = new AddEventRequest(
                "대규모 모임",
                List.of(addEventGameRequest1, addEventGameRequest2),
                List.of(addEventUserRequest1, addEventUserRequest2),
                eventStart,
                eventEnd
        );

        given(gameService.get(1L)).willReturn(mockGame);
        given(gameService.get(2L)).willReturn(game2);

        given(userService.get(1L)).willReturn(mockUser);
        given(userService.get(2L)).willReturn(user2);

        given(eventService.add(any(Event.class))).willAnswer(returnsFirstArg());

        // when
        Event result = addEventUseCase.addEvent(multiRequest);

        // then
        assertThat(result.getEventGames()).hasSize(2);
        assertThat(result.getEventUsers()).hasSize(2);
    }

    @Test
    @DisplayName("게임 없이 유저만 포함된 이벤트를 생성할 수 없다")
    void addEvent_withoutGames() {
        // given
        AddEventUserRequest addEventUserRequest1 = new AddEventUserRequest(1L);

        AddEventRequest requestWithoutGames = new AddEventRequest(
                "모임만",
                new ArrayList<>(),
                List.of(addEventUserRequest1),
                eventStart,
                eventEnd
        );

        // when & then
        assertThatThrownBy(() -> addEventUseCase.addEvent(requestWithoutGames)).isInstanceOf(EventGameRequiredException.class);
    }

    @Test
    @DisplayName("유저 없이 게임만 포함된 이벤트를 생성할 수 없다")
    void addEvent_withoutUsers() {
        // given
        AddEventGameRequest addEventGameRequest1 = new AddEventGameRequest(1L, gameStart, gameEnd);

        AddEventRequest requestWithoutUsers = new AddEventRequest(
                "모임만",
                List.of(addEventGameRequest1),
                new ArrayList<>(),
                eventStart,
                eventEnd
        );

        // when & then
        assertThatThrownBy(() -> addEventUseCase.addEvent(requestWithoutUsers)).isInstanceOf(EventUserRequiredException.class);
    }
}
