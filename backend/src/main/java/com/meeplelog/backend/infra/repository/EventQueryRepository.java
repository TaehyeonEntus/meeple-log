package com.meeplelog.backend.infra.repository;

import com.meeplelog.backend.feature.event.dto.EventDetail;
import com.meeplelog.backend.feature.event.dto.EventSummary;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.meeplelog.backend.domain.QEvent.event;
import static com.meeplelog.backend.domain.QEventGame.eventGame;
import static com.meeplelog.backend.domain.QEventUser.eventUser;

@Repository
@RequiredArgsConstructor
public class EventQueryRepository {
    private final JPAQueryFactory queryFactory;

    public EventDetail getEventDetail(Long eventId) {
        return queryFactory
                .select(
                        Projections.constructor(
                                EventDetail.class,
                                event.id,
                                event.name,
                                event.startTime,
                                event.endTime
                        )
                )
                .from(event)
                .where(eventEq(eventId))
                .fetchOne();
    }

    public List<EventSummary> getEventSummaries(Long userId, Long gameId) {
        return queryFactory
                .select(
                        Projections.constructor(EventSummary.class,
                                event.id,
                                event.name,
                                event.startTime,
                                event.endTime
                        )
                )
                .from(event)
                .join(event.eventGames, eventGame)
                .join(event.eventUsers, eventUser)
                .where(
                        gameEq(gameId),
                        userEq(userId)
                )
                .groupBy(event.id)
                .fetch();
    }

    private BooleanExpression eventEq(Long eventId) {
        return eventId != null
                ? event.id.eq(eventId)
                : null;
    }

    private BooleanExpression gameEq(Long gameId) {
        return gameId != null
                ? eventGame.game.id.eq(gameId)
                : null;
    }

    private BooleanExpression userEq(Long userId) {
        return userId != null
                ? eventUser.user.id.eq(userId)
                : null;
    }
}
