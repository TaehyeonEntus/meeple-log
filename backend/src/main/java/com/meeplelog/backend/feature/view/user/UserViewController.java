package com.meeplelog.backend.feature.view.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserViewController {
    private final GetUserResponseUseCase getUserResponseUseCase;

    @GetMapping("/{userId}")
    public ResponseEntity<?> getGameResponse(@PathVariable Long userId) {
        return ResponseEntity.ok(getUserResponseUseCase.getUserResponse(userId));
    }
}
