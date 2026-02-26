package com.meeplelog.backend.infra.repository;

import com.meeplelog.backend.domain.EventGame;
import com.meeplelog.backend.domain.Game;
import com.meeplelog.backend.feature.game.dto.GameSummary;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;



public interface EventGameRepository extends JpaRepository<EventGame, Long> {
    @Query("""
                select new com.meeplelog.backend.feature.game.dto.GameSummary(g.id, g.name, g.imageUrl)
                from EventGame eg
                join eg.game g
                group by g
                order by count(eg) desc
            """)
    List<GameSummary> getMostPlayedGameSummaries(Pageable pageable);

    @Query("""
                select new com.meeplelog.backend.feature.game.dto.GameSummary(g.id, g.name, g.imageUrl)
                from EventGame eg
                join eg.game g
                join eg.event e
                join e.eventUsers eu
                where eu.user = :user
                group by g
                order by count(eg) desc
            """)
    List<GameSummary> getMostPlayedGameSummariesByUser(long userId, Pageable pageable);

    @Query("""
                select new com.meeplelog.backend.feature.game.dto.GameSummary(g.id, g.name, g.imageUrl)
                from EventGame eg
                join eg.game g
                join g.categories gc
                where gc.category.id = :categoryId
                group by g
                order by count(eg) desc
            """)
    List<GameSummary> getMostPlayedGameSummariesByCategory(long categoryId, Pageable pageable);

    @Query("""
            select new com.meeplelog.backend.feature.game.dto.GameSummary(g.id, g.name, g.imageUrl)
            from EventGame eg
            join eg.game g
            group by g
            order by eg.end desc
            """)
    List<GameSummary> getRecentlyPlayedGameSummaries(Pageable pageable);

    @Query("""
            select new com.meeplelog.backend.feature.game.dto.GameSummary(g.id, g.name, g.imageUrl)
            from EventGame eg
            join eg.game g
            join eg.event e
            join e.eventUsers eu
            where eu.user = :user
            group by g
            order by eg.end desc
            """)
    List<GameSummary> getRecentlyPlayedGameSummariesByUser(long userId, Pageable pageable);
}
