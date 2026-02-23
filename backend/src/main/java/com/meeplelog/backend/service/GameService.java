package com.meeplelog.backend.service;

import com.meeplelog.backend.domain.Game;
import com.meeplelog.backend.infra.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameService {
    private final GameRepository gameRepository;

    public Game add(Game game){
        return gameRepository.save(game);
    }

    public Game get(long gameId){
        return gameRepository.findById(gameId).orElseThrow();
    }

    public boolean existsByName(String name){
        return gameRepository.existsByName(name);
    }
}
