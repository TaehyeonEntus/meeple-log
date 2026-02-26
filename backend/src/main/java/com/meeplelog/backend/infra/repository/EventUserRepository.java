package com.meeplelog.backend.infra.repository;

import com.meeplelog.backend.domain.EventUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventUserRepository extends JpaRepository<EventUser, Long> {
}
