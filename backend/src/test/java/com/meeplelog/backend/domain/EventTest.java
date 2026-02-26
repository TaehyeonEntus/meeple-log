package com.meeplelog.backend.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class EventTest {

    @Test
    @DisplayName("Event.of()로 이벤트를 생성한다")
    void of_success() {
        // given
        Instant start = Instant.parse("2024-01-01T10:00:00Z");
        Instant end = Instant.parse("2024-01-01T18:00:00Z");

        // when
        Event event = Event.of("보드게임 모임", start, end);

        // then
        assertThat(event).isNotNull();
        assertThat(event.getName()).isEqualTo("보드게임 모임");
        assertThat(event.getStart()).isEqualTo(start);
        assertThat(event.getEnd()).isEqualTo(end);
        assertThat(event.getEventGames()).isEmpty();
        assertThat(event.getEventUsers()).isEmpty();
    }

    @Test
    @DisplayName("Event.forTest()로 이벤트를 생성한다")
    void forTest_success() {
        // given
        Instant start = Instant.parse("2024-01-01T10:00:00Z");
        Instant end = Instant.parse("2024-01-01T18:00:00Z");

        // when
        Event event = Event.forTest(1L, "보드게임 모임", start, end);

        // then
        assertThat(event).isNotNull();
        assertThat(event.getId()).isEqualTo(1L);
        assertThat(event.getName()).isEqualTo("보드게임 모임");
        assertThat(event.getStart()).isEqualTo(start);
        assertThat(event.getEnd()).isEqualTo(end);
        assertThat(event.getEventGames()).isEmpty();
        assertThat(event.getEventUsers()).isEmpty();
    }

    @Test
    @DisplayName("이벤트에 게임을 추가할 수 있다")
    void addEventGame() {
        // given
        Instant eventStart = Instant.parse("2024-01-01T10:00:00Z");
        Instant eventEnd = Instant.parse("2024-01-01T18:00:00Z");
        Event event = Event.of("보드게임 모임", eventStart, eventEnd);

        Instant gameStart = Instant.parse("2024-01-01T10:00:00Z");
        Instant gameEnd = Instant.parse("2024-01-01T12:00:00Z");
        Game game = Game.of("스플렌더", "http://image.url");
        EventGame eventGame = EventGame.of(gameStart, gameEnd, event, game);

        // when
        event.getEventGames().add(eventGame);

        // then
        assertThat(event.getEventGames()).hasSize(1);
        assertThat(event.getEventGames().getFirst().getGame()).isEqualTo(game);
    }

    @Test
    @DisplayName("이벤트에 사용자를 추가할 수 있다")
    void addEventUser() {
        // given
        Instant start = Instant.parse("2024-01-01T10:00:00Z");
        Instant end = Instant.parse("2024-01-01T18:00:00Z");
        Event event = Event.of("보드게임 모임", start, end);

        User user = User.of("테스트유저", "testUser", "password", null);
        EventUser eventUser = EventUser.of(event, user);

        // when
        event.getEventUsers().add(eventUser);

        // then
        assertThat(event.getEventUsers()).hasSize(1);
        assertThat(event.getEventUsers().getFirst().getUser()).isEqualTo(user);
    }

    @Test
    @DisplayName("이벤트에 여러 게임과 사용자를 추가할 수 있다")
    void addMultipleGamesAndUsers() {
        // given
        Instant eventStart = Instant.parse("2024-01-01T10:00:00Z");
        Instant eventEnd = Instant.parse("2024-01-01T18:00:00Z");
        Event event = Event.of("보드게임 모임", eventStart, eventEnd);

        Game game1 = Game.of("스플렌더", "http://image1.url");
        Game game2 = Game.of("카탄", "http://image2.url");
        EventGame eventGame1 = EventGame.of(eventStart, eventEnd, event, game1);
        EventGame eventGame2 = EventGame.of(eventStart, eventEnd, event, game2);

        User user1 = User.of("유저1", "user1", "password", null);
        User user2 = User.of("유저2", "user2", "password", null);
        EventUser eventUser1 = EventUser.of(event, user1);
        EventUser eventUser2 = EventUser.of(event, user2);

        // when
        event.getEventGames().add(eventGame1);
        event.getEventGames().add(eventGame2);
        event.getEventUsers().add(eventUser1);
        event.getEventUsers().add(eventUser2);

        // then
        assertThat(event.getEventGames()).hasSize(2);
        assertThat(event.getEventUsers()).hasSize(2);
    }
}
