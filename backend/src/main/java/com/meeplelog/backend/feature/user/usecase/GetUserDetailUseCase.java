package com.meeplelog.backend.feature.user.usecase;

import com.meeplelog.backend.feature.user.dto.UserDetail;
import com.meeplelog.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetUserDetailUseCase {
    private final UserService userService;

    public UserDetail getUserDetail(long userId){
        return UserDetail.of(userService.get(userId));
    }
}