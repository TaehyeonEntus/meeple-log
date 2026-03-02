package com.meeplelog.backend.infra.repository;

import com.meeplelog.backend.feature.category.dto.CategoryDetail;
import com.meeplelog.backend.feature.category.dto.CategorySummary;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.meeplelog.backend.domain.QCategory.category;

@Repository
@RequiredArgsConstructor
public class CategoryQueryRepository {
    private final JPAQueryFactory queryFactory;

    public CategoryDetail getCategoryDetail(Long categoryId) {
        return queryFactory
                .select(
                        Projections.constructor(CategoryDetail.class,
                                category.id,
                                category.name,
                                category.description
                        )
                )
                .from(category)
                .where(
                        categoryEq(categoryId)
                )
                .fetchOne();
    }

    public List<CategorySummary> getAllCategorySummaries() {
        return queryFactory
                .select(Projections.constructor(CategorySummary.class, category.id, category.name))
                .from(category)
                .fetch();
    }

    private BooleanExpression categoryEq(Long categoryId) {
        return categoryId != null
                ? category.id.eq(categoryId)
                : null;
    }
}
