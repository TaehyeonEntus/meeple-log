package com.meeplelog.backend.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Instant start;
    private Instant end;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<EventGame> eventGames = new ArrayList<>();

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<EventUser> eventUsers = new ArrayList<>();


    public static Event of(String name, Instant start, Instant end){
        return new Event(name, start, end);
    }

    public static Event forTest(Long id, String name, Instant start, Instant end){
        return new Event(id, name, start, end);
    }

    private Event(String name, Instant start, Instant end) {
        this.name = name;
        this.start = start;
        this.end = end;
    }
}
