package com.meeplelog.backend.feature.home.usecase;

import com.meeplelog.backend.feature.category.dto.CategorySummary;
import com.meeplelog.backend.feature.category.usecase.GetCategorySummariesUseCase;
import com.meeplelog.backend.feature.game.dto.GameSummary;
import com.meeplelog.backend.feature.game.usecase.GetMostPlayedGameSummariesUseCase;
import com.meeplelog.backend.feature.game.usecase.GetRecentlyPlayedGameSummariesUseCase;
import com.meeplelog.backend.feature.home.web.dto.HomeResponse;
import com.meeplelog.backend.feature.user.dto.UserDetail;
import com.meeplelog.backend.feature.user.usecase.GetUserDetailUseCase;
import com.meeplelog.backend.security.SecurityUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class GetHomeResponseUseCaseTest {

    @Mock
    private SecurityUtil securityUtil;

    @Mock
    private GetUserDetailUseCase getUserDetailUseCase;

    @Mock
    private GetCategorySummariesUseCase getCategorySummariesUseCase;

    @Mock
    private GetMostPlayedGameSummariesUseCase getMostPlayedGameSummariesUseCase;

    @Mock
    private GetRecentlyPlayedGameSummariesUseCase getRecentlyPlayedGameSummariesUseCase;

    @InjectMocks
    private GetHomeResponseUseCase getHomeResponseUseCase;

    @Test
    @DisplayName("인증된 사용자의 홈 응답을 생성한다")
    void getHomeResponse_authenticatedUser() {
        // given
        Authentication authentication = mock(Authentication.class);
        given(authentication.isAuthenticated()).willReturn(true);

        UserDetail userDetail = mock(UserDetail.class);
        List<CategorySummary> categories = List.of();
        List<GameSummary> mostPlayed = List.of();
        List<GameSummary> recentlyPlayed = List.of();

        given(securityUtil.getIdFromAuthentication(authentication)).willReturn(1L);
        given(getUserDetailUseCase.getUserDetail(1L)).willReturn(userDetail);
        given(getCategorySummariesUseCase.getCategorySummaries()).willReturn(categories);
        given(getMostPlayedGameSummariesUseCase.getMostPlayedGameSummaries(1L, null, 10)).willReturn(mostPlayed);
        given(getRecentlyPlayedGameSummariesUseCase.getRecentlyPlayedGameSummaries(1L, null, 10)).willReturn(recentlyPlayed);

        // when
        HomeResponse result = getHomeResponseUseCase.getHomeResponse(authentication);

        // then
        assertThat(result).isNotNull();
        verify(getUserDetailUseCase).getUserDetail(1L);
        verify(getMostPlayedGameSummariesUseCase).getMostPlayedGameSummaries(1L, null, 10);
        verify(getRecentlyPlayedGameSummariesUseCase).getRecentlyPlayedGameSummaries(1L, null, 10);
    }

    @Test
    @DisplayName("인증되지 않은 사용자의 홈 응답을 생성한다")
    void getHomeResponse_unauthenticatedUser() {
        // given
        Authentication authentication = mock(Authentication.class);
        given(authentication.isAuthenticated()).willReturn(false);

        List<CategorySummary> categories = List.of();
        List<GameSummary> mostPlayed = List.of();
        List<GameSummary> recentlyPlayed = List.of();

        given(getCategorySummariesUseCase.getCategorySummaries()).willReturn(categories);
        given(getMostPlayedGameSummariesUseCase.getMostPlayedGameSummaries(null, null, 10)).willReturn(mostPlayed);
        given(getRecentlyPlayedGameSummariesUseCase.getRecentlyPlayedGameSummaries(null, null, 10)).willReturn(recentlyPlayed);

        // when
        HomeResponse response = getHomeResponseUseCase.getHomeResponse(authentication);

        // then
        verify(getMostPlayedGameSummariesUseCase).getMostPlayedGameSummaries(null, null, 10);
        verify(getRecentlyPlayedGameSummariesUseCase).getRecentlyPlayedGameSummaries(null, null, 10);
    }
}
