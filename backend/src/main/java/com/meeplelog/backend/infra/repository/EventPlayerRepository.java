package com.meeplelog.backend.infra.repository;

import com.meeplelog.backend.domain.EventPlayer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventPlayerRepository extends JpaRepository<EventPlayer, Long> {
}
