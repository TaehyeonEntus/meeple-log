package com.meeplelog.backend.usecase.addGame;

import com.meeplelog.backend.domain.Game;
import com.meeplelog.backend.exception.DuplicateNameException;
import com.meeplelog.backend.service.GameService;
import com.meeplelog.backend.usecase.addGame.dto.AddGameRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddGameUseCase {
    private final GameService gameService;

    public void addGame(AddGameRequest request){
        String name = request.name();

        validateNameUniqueness(name);

        gameService.add(Game.of(name));
    }

    private void validateNameUniqueness(String name){
        if(gameService.existsByName(name))
            throw new DuplicateNameException("이미 존재하는 이름입니다.");
    }
}
