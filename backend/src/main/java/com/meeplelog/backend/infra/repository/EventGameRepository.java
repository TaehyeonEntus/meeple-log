package com.meeplelog.backend.infra.repository;

import com.meeplelog.backend.domain.EventGame;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventGameRepository extends JpaRepository<EventGame, Long> {
}
