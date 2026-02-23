package com.meeplelog.backend.domain;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
public class EventGame {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    private Instant start;
    private Instant end;
}
