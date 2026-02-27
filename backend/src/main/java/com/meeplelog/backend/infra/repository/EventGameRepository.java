package com.meeplelog.backend.infra.repository;

import com.meeplelog.backend.domain.EventGame;
import com.meeplelog.backend.domain.Game;
import com.meeplelog.backend.feature.game.dto.GameSummary;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;



public interface EventGameRepository extends JpaRepository<EventGame, Long> {
}
