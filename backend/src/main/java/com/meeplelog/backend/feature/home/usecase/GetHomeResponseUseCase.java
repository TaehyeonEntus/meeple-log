package com.meeplelog.backend.feature.home.usecase;

import com.meeplelog.backend.feature.category.usecase.GetCategorySummariesUseCase;
import com.meeplelog.backend.feature.game.usecase.GetMostPlayedGameSummariesUseCase;
import com.meeplelog.backend.feature.game.usecase.GetRecentlyPlayedGameSummariesUseCase;
import com.meeplelog.backend.feature.home.web.dto.HomeResponse;
import com.meeplelog.backend.feature.user.usecase.GetUserDetailUseCase;
import com.meeplelog.backend.security.CustomUserDetails;
import com.meeplelog.backend.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetHomeResponseUseCase {
    private final SecurityUtil securityUtil;
    private final GetUserDetailUseCase getUserDetailUseCase;
    private final GetCategorySummariesUseCase getCategorySummariesUseCase;
    private final GetMostPlayedGameSummariesUseCase getMostPlayedGameSummariesUseCase;
    private final GetRecentlyPlayedGameSummariesUseCase getRecentlyPlayedGameSummariesUseCase;

    public HomeResponse getHomeResponse(Authentication authentication) {
        if (authentication.isAuthenticated() | authentication instanceof CustomUserDetails) {
            long userId = securityUtil.getIdFromAuthentication(authentication);
            return HomeResponse.of(
                    getUserDetailUseCase.getUserDetail(userId),
                    getCategorySummariesUseCase.getCategorySummaries(),
                    getMostPlayedGameSummariesUseCase.getMostPlayedGameSummaries(userId, null, 10),
                    getRecentlyPlayedGameSummariesUseCase.getRecentlyPlayedGameSummaries(userId, null, 10));
        } else {
            return HomeResponse.of(
                    null,
                    getCategorySummariesUseCase.getCategorySummaries(),
                    getMostPlayedGameSummariesUseCase.getMostPlayedGameSummaries(null, null, 10),
                    getRecentlyPlayedGameSummariesUseCase.getRecentlyPlayedGameSummaries(null, null, 10));
        }
    }
}
