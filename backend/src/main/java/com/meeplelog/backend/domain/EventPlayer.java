package com.meeplelog.backend.domain;

import jakarta.persistence.*;

@Entity
public class EventPlayer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;
}
