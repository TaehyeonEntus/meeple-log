package com.meeplelog.backend.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String imageUrl;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<GameCategory> categories = new ArrayList<>();

    public static Game of(String name, String imageUrl) {
        return new Game(name, imageUrl);
    }

    public static Game forTest(long id, String name, String imageUrl){
        return new Game(id, name, imageUrl);
    }

    private Game(String name, String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public void addCategory(Category category) {
        categories.add(GameCategory.of(this, category));
    }
}
