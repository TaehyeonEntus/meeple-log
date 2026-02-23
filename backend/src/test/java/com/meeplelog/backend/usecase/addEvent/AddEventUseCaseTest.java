package com.meeplelog.backend.usecase.addEvent;

import com.meeplelog.backend.domain.Event;
import com.meeplelog.backend.domain.Game;
import com.meeplelog.backend.domain.Player;
import com.meeplelog.backend.service.EventService;
import com.meeplelog.backend.service.GameService;
import com.meeplelog.backend.service.PlayerService;
import com.meeplelog.backend.usecase.addEvent.dto.AddEventGameRequest;
import com.meeplelog.backend.usecase.addEvent.dto.AddEventPlayerRequest;
import com.meeplelog.backend.usecase.addEvent.dto.AddEventRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AddEventUseCaseTest {

    @InjectMocks
    private AddEventUseCase addEventUseCase;

    @Mock
    private EventService eventService;

    @Mock
    private PlayerService playerService;

    @Mock
    private GameService gameService;

    @Test
    @DisplayName("이벤트 추가 성공")
    void addEvent_success() {
        // given
        Instant eventStart = Instant.now();
        Instant eventEnd = eventStart.plusSeconds(3600);

        AddEventRequest request = new AddEventRequest(
                "Test Event",
                List.of(new AddEventGameRequest(1L, eventStart, eventEnd)),
                List.of(new AddEventPlayerRequest(1L)),
                eventStart,
                eventEnd
        );

        Player mockPlayer = Player.of("Player Name1", "username1", "password1");
        Game mockGame = Game.of("Game Name1");

        when(playerService.get(1L)).thenReturn(mockPlayer);
        when(gameService.get(1L)).thenReturn(mockGame);
        when(eventService.add(any())).thenAnswer(returnsFirstArg());

        // when
        Event event = addEventUseCase.addEvent(request);

        // then
        Assertions.assertEquals("Test Event", event.getName());

        Assertions.assertEquals(1, event.getEventPlayers().size());
        Assertions.assertEquals("Player Name1", event.getEventPlayers().getFirst().getPlayer().getName());

        Assertions.assertEquals(1, event.getEventGames().size());
        Assertions.assertEquals("Game Name1", event.getEventGames().getFirst().getGame().getName());

        Assertions.assertEquals(eventStart, event.getStart());
        Assertions.assertEquals(eventEnd, event.getEnd());
    }
}
