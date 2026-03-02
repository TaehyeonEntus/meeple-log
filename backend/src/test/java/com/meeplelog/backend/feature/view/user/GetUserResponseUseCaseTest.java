package com.meeplelog.backend.feature.view.user;

import com.meeplelog.backend.feature.user.dto.UserDetail;
import com.meeplelog.backend.feature.user.usecase.GetUserDetailUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class GetUserResponseUseCaseTest {

    @InjectMocks
    private GetUserResponseUseCase getUserResponseUseCase;

    @Mock
    private GetUserDetailUseCase getUserDetailUseCase;

    @Test
    @DisplayName("유저 상세 정보를 조회한다")
    void getUserResponse() {
        // given
        Long userId = 1L;
        UserDetail userDetail = new UserDetail(userId, "User Name", "http://image.url");
        given(getUserDetailUseCase.getUserDetail(userId)).willReturn(userDetail);

        // when
        UserResponse result = getUserResponseUseCase.getUserResponse(userId);

        // then
        assertThat(result.userDetail()).isEqualTo(userDetail);
    }
}
