package com.meeplelog.backend.feature.user.dto;

import com.meeplelog.backend.domain.User;

public record UserSummary(
        Long userId,
        String name,
        String imageUrl
) {
    public static UserSummary of(User user) {
        return new UserSummary(user.getId(), user.getName(), user.getImageUrl());
    }
}
