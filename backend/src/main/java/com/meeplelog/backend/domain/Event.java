package com.meeplelog.backend.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private Instant start;
    private Instant end;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<EventGame> eventGames = new ArrayList<>();

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<EventPlayer> eventPlayers = new ArrayList<>();


    public static Event of(String name, Instant start, Instant end){
        return new Event(name, start, end);
    }

    private Event(String name, Instant start, Instant end) {
        this.name = name;
        this.start = start;
        this.end = end;
    }
}
