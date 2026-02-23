package com.meeplelog.backend.infra.repository;

import com.meeplelog.backend.domain.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {
    boolean existsByName(String name);
}
