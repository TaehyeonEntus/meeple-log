package com.meeplelog.backend.feature.user.dto;

import com.meeplelog.backend.domain.User;

public record UserDetail(
        long userId,
        String name,
        String imageUrl
) {
    public static UserDetail of(User user) {
        return new UserDetail(user.getId(), user.getName(), user.getImageUrl());
    }
}
