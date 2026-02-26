package com.meeplelog.backend.feature.game.usecase;

import com.meeplelog.backend.domain.Game;
import com.meeplelog.backend.exception.DuplicateNameException;
import com.meeplelog.backend.service.GameService;
import com.meeplelog.backend.feature.game.web.dto.AddGameRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddGameUseCase {
    private final GameService gameService;

    public Game addGame(AddGameRequest request){
        validateNameUniqueness(request.gameName());

        return gameService.add(Game.of(request.gameName(), request.imageUrl()));
    }

    private void validateNameUniqueness(String name){
        if(gameService.existsByName(name))
            throw new DuplicateNameException("이미 존재하는 이름입니다.");
    }
}
