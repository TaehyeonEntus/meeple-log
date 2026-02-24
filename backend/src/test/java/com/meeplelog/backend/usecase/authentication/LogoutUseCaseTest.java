package com.meeplelog.backend.usecase.authentication;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LogoutUseCaseTest {

    @InjectMocks
    private LogoutUseCase logoutUseCase;

    @Test
    @DisplayName("로그아웃 성공")
    void logout_success() {
        // given
        // 현재는 로그아웃 로직이 없으므로 별도의 given 절이 필요 없음

        // when
        logoutUseCase.logout();

        // then
        // 예외가 발생하지 않으면 성공으로 간주
    }
}
