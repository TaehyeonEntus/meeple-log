package com.meeplelog.backend.feature.view.home;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
@RequiredArgsConstructor
public class HomeViewController {
    private final GetHomeResponseUseCase getHomeResponseUseCase;

    @GetMapping
    public ResponseEntity<?> getHomeResponse(Authentication authentication) {
        return ResponseEntity.ok(getHomeResponseUseCase.getHomeResponse(authentication));
    }
}
