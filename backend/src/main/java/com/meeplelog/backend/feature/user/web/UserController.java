package com.meeplelog.backend.feature.user.web;

import com.meeplelog.backend.feature.user.usecase.GetUserDetailUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final GetUserDetailUseCase getGameDetailUseCase;

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserDetail(@PathVariable long userId){
        return ResponseEntity.ok(getGameDetailUseCase.getUserDetail(userId));
    }
}
