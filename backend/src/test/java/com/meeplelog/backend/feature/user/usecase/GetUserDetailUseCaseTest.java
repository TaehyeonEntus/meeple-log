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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class GetUserDetailUseCaseTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private GetUserDetailUseCase getUserDetailUseCase;

    @Test
    @DisplayName("사용자 상세 정보를 조회한다")
    void getUserDetail_success() {
        // given
        long userId = 1L;
        User user = User.forTest(userId, "Test User", "testuser", "password", "http://image.url");
        given(userService.get(userId)).willReturn(user);

        // when
        UserDetail result = getUserDetailUseCase.getUserDetail(userId);

        // then
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(userId);
        assertThat(result.name()).isEqualTo("Test User");
        assertThat(result.imageUrl()).isEqualTo("http://image.url");
        verify(userService).get(userId);
    }
}
