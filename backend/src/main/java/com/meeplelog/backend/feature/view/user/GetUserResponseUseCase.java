package com.meeplelog.backend.feature.view.user;

import com.meeplelog.backend.feature.user.usecase.GetUserDetailUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetUserResponseUseCase {
    private final GetUserDetailUseCase getUserDetailUseCase;

    public UserResponse getUserResponse(Long userId) {
        return UserResponse.of(getUserDetailUseCase.getUserDetail(userId));
    }
}
