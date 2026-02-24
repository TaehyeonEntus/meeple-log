package com.meeplelog.backend.infra.repository;

import com.meeplelog.backend.domain.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player, Long> {
    boolean existsByName(String name);
    boolean existsByUsername(String username);
    Optional<Player> findByUsername(String username);
}
