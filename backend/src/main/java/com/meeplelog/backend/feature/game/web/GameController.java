package com.meeplelog.backend.feature.game.web;

import com.meeplelog.backend.feature.game.usecase.AddGameUseCase;
import com.meeplelog.backend.feature.game.web.dto.AddGameRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/game")
@RequiredArgsConstructor
public class GameController {
    private final AddGameUseCase addGameUseCase;

    @PostMapping("/add")
    public ResponseEntity<?> addGame(@RequestBody AddGameRequest request) {
        addGameUseCase.addGame(request);

        return ResponseEntity.ok().body(Map.of("message", "게임 추가 완료"));
    }
}
