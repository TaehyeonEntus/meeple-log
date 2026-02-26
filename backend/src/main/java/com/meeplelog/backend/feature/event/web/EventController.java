package com.meeplelog.backend.feature.event.web;

import com.meeplelog.backend.feature.event.usecase.AddEventUseCase;
import com.meeplelog.backend.feature.event.web.dto.AddEventUserRequest;
import com.meeplelog.backend.feature.event.web.dto.AddEventRequest;
import com.meeplelog.backend.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/event")
@RequiredArgsConstructor
public class EventController {
    private final SecurityUtil securityUtil;
    private final AddEventUseCase addEventUseCase;

    @PostMapping("/add")
    public ResponseEntity<?> addEvent(
            Authentication authentication,
            @RequestBody AddEventRequest request) {
        //Event Users에 본인 추가
        request.eventUsers().add(new AddEventUserRequest(securityUtil.getIdFromAuthentication(authentication)));

        addEventUseCase.addEvent(request);

        return ResponseEntity.ok().body(Map.of("message", "이벤트 추가 완료"));
    }
}
