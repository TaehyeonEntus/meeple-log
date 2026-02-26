package com.meeplelog.backend.feature.authentication.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutUseCase {
    public void logout() {
        //todo 레디스 추가하면 여기서 refresh 지우기 ~~~~
    }
}
