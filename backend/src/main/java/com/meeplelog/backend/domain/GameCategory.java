package com.meeplelog.backend.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GameCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    private Game game;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    public static GameCategory of(Game game, Category category){
        return new GameCategory(game, category);
    }

    public static GameCategory forTest(Long id, Game game, Category category){
        return new GameCategory(id, game, category);
    }

    private GameCategory(Game game, Category category){
        this.game = game;
        this.category = category;
    }
}