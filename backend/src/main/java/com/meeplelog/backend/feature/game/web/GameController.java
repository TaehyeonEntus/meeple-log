package com.meeplelog.backend.feature.game.web;

import com.meeplelog.backend.feature.game.usecase.AddGameUseCase;
import com.meeplelog.backend.feature.game.usecase.GetGameDetailUseCase;
import com.meeplelog.backend.feature.game.web.dto.AddGameRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/game")
@RequiredArgsConstructor
public class GameController {
    private final AddGameUseCase addGameUseCase;
    private final GetGameDetailUseCase getGameDetailUseCase;

    @PostMapping("/add")
    public ResponseEntity<?> addGame(
            @RequestBody AddGameRequest request) {
        addGameUseCase.addGame(request);

        return ResponseEntity.ok().body(Map.of("message", "게임 추가 완료"));
    }

    @GetMapping("/{gameId}")
    public ResponseEntity<?> getGameDetail(@PathVariable long gameId){
        return ResponseEntity.ok(getGameDetailUseCase.getGameDetail(gameId));
    }
}
