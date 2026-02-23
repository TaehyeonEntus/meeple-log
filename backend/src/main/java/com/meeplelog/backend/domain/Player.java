package com.meeplelog.backend.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String username;
    private String password;

    public static Player of(String name, String username, String password){
        return new Player(name, username, password);
    }

    private Player(String name, String username, String password){
        this.name = name;
        this.username = username;
        this.password = password;
    }
}
