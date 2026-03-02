package com.meeplelog.backend.infra.repository;

import com.meeplelog.backend.feature.event.dto.EventGameSummary;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.meeplelog.backend.domain.QEvent.event;
import static com.meeplelog.backend.domain.QEventGame.eventGame;

@Repository
@RequiredArgsConstructor
public class EventGameQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<EventGameSummary> getEventGameSummaries(Long eventId) {
        return queryFactory
                .select(
                        Projections.constructor(EventGameSummary.class,
                                eventGame.game.id,
                                eventGame.game.name,
                                eventGame.game.imageUrl,
                                eventGame.startTime,
                                eventGame.endTime
                        )
                )
                .from(event)
                .join(event.eventGames, eventGame)
                .where(eventEq(eventId))
                .fetch();
    }

    private BooleanExpression eventEq(Long eventId) {
        return eventId != null
                ? event.id.eq(eventId)
                : null;
    }
}
