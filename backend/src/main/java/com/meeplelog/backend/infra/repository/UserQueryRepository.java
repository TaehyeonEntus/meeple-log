package com.meeplelog.backend.infra.repository;

import com.meeplelog.backend.feature.user.dto.UserSummary;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.meeplelog.backend.domain.QEventUser.eventUser;
import static com.meeplelog.backend.domain.QUser.user;

@Repository
@RequiredArgsConstructor
public class UserQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<UserSummary> getUserSummariesByEvent(Long eventId) {
        return queryFactory
                .select(Projections.constructor(UserSummary.class, user.id, user.name, user.imageUrl))
                .from(user)
                .where(
                        eventEq(eventId)
                )
                .fetch();
    }

    private BooleanExpression eventEq(Long eventId) {
        return eventId != null
                ? JPAExpressions
                .selectOne()
                .from(eventUser)
                .where(
                        eventUser.user.id.eq(user.id),
                        eventUser.event.id.eq(eventId)
                )
                .exists()
                : null;
    }
}
