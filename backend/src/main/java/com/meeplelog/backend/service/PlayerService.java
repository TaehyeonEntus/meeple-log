package com.meeplelog.backend.service;

import com.meeplelog.backend.domain.Player;
import com.meeplelog.backend.infra.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlayerService {
    private final PlayerRepository playerRepository;

    public Player add(Player player){
        return playerRepository.save(player);
    }

    public Player get(long playerId){
        return playerRepository.findById(playerId).orElseThrow();
    }

    public boolean existsByName(String name){
        return playerRepository.existsByName(name);
    }

    public boolean existsByUsername(String username){
        return playerRepository.existsByUsername(username);
    }

    public Player getByUsername(String username) {
        return playerRepository.findByUsername(username).orElseThrow();
    }
}
