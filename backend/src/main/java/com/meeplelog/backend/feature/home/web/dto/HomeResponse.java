package com.meeplelog.backend.feature.home.web.dto;

import com.meeplelog.backend.feature.category.dto.CategorySummary;
import com.meeplelog.backend.feature.game.dto.GameSummary;
import com.meeplelog.backend.feature.user.dto.UserDetail;

import java.util.List;

public record HomeResponse(
        UserDetail user,
        List<CategorySummary> categories,
        List<GameSummary> mostPlayedGames,
        List<GameSummary> recentlyPlayedGames
) {
    public static HomeResponse of(UserDetail user, List<CategorySummary> categories, List<GameSummary> mostPlayedGames, List<GameSummary> recentlyPlayedGames){
        return new HomeResponse(user, categories, mostPlayedGames, recentlyPlayedGames);
    }
}
