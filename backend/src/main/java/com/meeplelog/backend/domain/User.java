package com.meeplelog.backend.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String username;
    private String password;

    private String imageUrl;

    public static User of(String name, String username, String password, String imageUrl){
        return new User(name, username, password, imageUrl);
    }

    public static User forTest(long id, String name, String username, String password, String imageUrl){
        return new User(id, name, username, password, imageUrl);
    }

    private User(String name, String username, String password, String imageUrl){
        this.name = name;
        this.username = username;
        this.password = password;
        this.imageUrl = imageUrl;
    }
}
