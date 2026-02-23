package com.meeplelog.backend.domain;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.List;

@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @OneToMany(mappedBy = "event")
    private List<EventGame> eventGames;

    @OneToMany(mappedBy = "event")
    private List<EventPlayer> eventPlayers;

    private Instant start;
    private Instant end;
}
