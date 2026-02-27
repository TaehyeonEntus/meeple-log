package com.meeplelog.backend.infra.repository;

import com.meeplelog.backend.feature.game.dto.GameSummary;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.meeplelog.backend.domain.QEvent.event;
import static com.meeplelog.backend.domain.QEventGame.eventGame;
import static com.meeplelog.backend.domain.QEventUser.eventUser;
import static com.meeplelog.backend.domain.QGame.game;
import static com.meeplelog.backend.domain.QGameCategory.gameCategory;

@Repository
@RequiredArgsConstructor
public class GameQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<GameSummary> getMostPlayedGameSummaries(Long userId, Long categoryId, int limit) {
        return queryFactory
                .select(Projections.constructor(GameSummary.class, game.id, game.name, game.imageUrl))
                .from(game)
                .where(
                        userEq(userId),
                        categoryEq(categoryId)
                )
                .orderBy(mostPlayed())
                .limit(limit)
                .fetch();
    }

    public List<GameSummary> getRecentlyPlayedGameSummaries(Long userId, Long categoryId, int limit) {
        return queryFactory
                .select(Projections.constructor(GameSummary.class, game.id, game.name, game.imageUrl))
                .from(game)
                .where(
                        userEq(userId),
                        categoryEq(categoryId)
                )
                .orderBy(recentlyPlayed())
                .limit(limit)
                .fetch();
    }

    //조건
    private BooleanExpression categoryEq(Long categoryId) {
        return categoryId != null
                ? JPAExpressions
                .selectOne()
                .from(gameCategory)
                .where(
                        gameCategory.game.id.eq(game.id),
                        gameCategory.category.id.eq(categoryId)
                )
                .exists()
                : null;
    }

    private BooleanExpression userEq(Long userId) {
        return userId != null
                ? JPAExpressions
                .selectOne()
                .from(event)
                .join(eventUser).on(eventUser.event.id.eq(event.id))
                .join(eventGame).on(eventGame.event.id.eq(event.id))
                .where(
                        eventGame.game.id.eq(game.id),
                        eventUser.user.id.eq(userId)
                )
                .exists()
                : null;
    }

    //정렬
    private OrderSpecifier<?> mostPlayed() {
        return new OrderSpecifier<>(
                Order.DESC,
                JPAExpressions
                        .select(eventGame.count())
                        .from(eventGame)
                        .where(eventGame.game.id.eq(game.id))
        );
    }

    private OrderSpecifier<?> recentlyPlayed() {
        return new OrderSpecifier<>(
                Order.DESC,
                JPAExpressions
                        .select(eventGame.end.max())
                        .from(eventGame)
                        .where(eventGame.game.id.eq(game.id))
        );
    }
}
