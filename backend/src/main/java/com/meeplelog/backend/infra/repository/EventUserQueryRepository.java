package com.meeplelog.backend.infra.repository;

import com.meeplelog.backend.feature.event.dto.EventUserSummary;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.meeplelog.backend.domain.QEvent.event;
import static com.meeplelog.backend.domain.QEventUser.eventUser;

@Repository
@RequiredArgsConstructor
public class EventUserQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<EventUserSummary> getEventUserSummaries(Long eventId) {
        return queryFactory
                .select(
                        Projections.constructor(EventUserSummary.class,
                                eventUser.user.id,
                                eventUser.user.name,
                                eventUser.user.imageUrl
                        )
                )
                .from(event)
                .join(event.eventUsers, eventUser)
                .where(
                        eventEq(eventId)
                )
                .fetch();
    }

    private BooleanExpression eventEq(Long eventId) {
        return eventId != null
                ? event.id.eq(eventId)
                : null;
    }
}
