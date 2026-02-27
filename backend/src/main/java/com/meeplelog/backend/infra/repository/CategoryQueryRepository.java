package com.meeplelog.backend.infra.repository;

import com.meeplelog.backend.feature.category.dto.CategorySummary;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.meeplelog.backend.domain.QCategory.category;

@Repository
@RequiredArgsConstructor
public class CategoryQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<CategorySummary> getAllCategorySummaries() {
        return queryFactory
                .select(Projections.constructor(CategorySummary.class, category.id, category.name))
                .from(category)
                .fetch();
    }
}
