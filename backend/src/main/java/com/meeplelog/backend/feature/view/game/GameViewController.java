package com.meeplelog.backend.feature.view.game;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/game")
@RequiredArgsConstructor
public class GameViewController {
    private final GetGameResponseUseCase getGameResponseUseCase;

    @GetMapping("/{gameId}")
    public ResponseEntity<?> getGameResponse(@PathVariable Long gameId) {
        return ResponseEntity.ok(getGameResponseUseCase.getGameResponse(gameId));
    }
}
