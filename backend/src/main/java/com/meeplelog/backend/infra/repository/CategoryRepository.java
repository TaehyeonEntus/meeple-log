package com.meeplelog.backend.infra.repository;

import com.meeplelog.backend.domain.Category;
import com.meeplelog.backend.feature.category.dto.CategorySummary;
import com.meeplelog.backend.feature.game.dto.GameSummary;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
