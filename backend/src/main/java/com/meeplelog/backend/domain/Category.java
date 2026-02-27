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
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    private String description;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<GameCategory> games = new ArrayList<>();

    public static Category of(String name, String description) {
        return new Category(name, description);
    }

    public static Category forTest(Long id, String name, String description){
        return new Category(id, name, description);
    }

    private Category(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
