package com.meeplelog.backend.feature.view.user;

import com.meeplelog.backend.feature.user.dto.UserDetail;

public record UserResponse(
        UserDetail userDetail
) {
    public static UserResponse of(UserDetail userDetail) {
        return new UserResponse(userDetail);
    }
}
