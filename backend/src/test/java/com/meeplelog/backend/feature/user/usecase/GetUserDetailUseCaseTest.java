package com.meeplelog.backend.feature.user.usecase;

import com.meeplelog.backend.domain.User;
import com.meeplelog.backend.feature.user.dto.UserDetail;
import com.meeplelog.backend.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class GetUserDetailUseCaseTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private GetUserDetailUseCase getUserDetailUseCase;

    @Test
    @DisplayName("사용자 ID로 사용자 상세 정보를 조회한다")
    void getUserDetail_success() {
        // given
        User user = User.of("테스트유저", "testUser", "password", null);
        given(userService.get(1L)).willReturn(user);

        // when
        UserDetail result = getUserDetailUseCase.getUserDetail(1L);

        // then
        assertThat(result).isNotNull();
        verify(userService).get(1L);
    }

    @Test
    @DisplayName("존재하지 않는 사용자 ID로 조회 시 예외가 발생한다")
    void getUserDetail_notFound_throwsException() {
        // given
        given(userService.get(999L)).willThrow(NoSuchElementException.class);

        // when & then
        assertThatThrownBy(() -> getUserDetailUseCase.getUserDetail(999L))
                .isInstanceOf(NoSuchElementException.class);
    }
}
