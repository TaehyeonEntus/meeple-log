package com.meeplelog.backend.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class EventGame {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Instant start;
    private Instant end;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    private Game game;

    public static EventGame of(Instant start, Instant end, Event event, Game game) {
        return new EventGame(start, end, event, game);
    }

    public static EventGame forTest(Long id, Instant start, Instant end, Event event, Game game){
        return new EventGame(id, start, end, event, game);
    }

    private EventGame(Instant start, Instant end, Event event, Game game) {
        this.start = start;
        this.end = end;
        this.event = event;
        this.game = game;
    }
}
