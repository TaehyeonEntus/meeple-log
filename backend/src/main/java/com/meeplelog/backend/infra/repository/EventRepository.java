package com.meeplelog.backend.infra.repository;

import com.meeplelog.backend.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
