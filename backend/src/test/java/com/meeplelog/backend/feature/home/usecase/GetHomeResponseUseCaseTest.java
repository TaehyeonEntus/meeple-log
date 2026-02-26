package com.meeplelog.backend.feature.home.usecase;

import com.meeplelog.backend.feature.category.dto.CategorySummary;
import com.meeplelog.backend.feature.category.usecase.GetCategorySummariesUseCase;
import com.meeplelog.backend.feature.game.dto.GameSummary;
import com.meeplelog.backend.feature.game.usecase.GetMostPlayedGameSummariesUseCase;
import com.meeplelog.backend.feature.game.usecase.GetRecentlyPlayedGameSummariesUseCase;
import com.meeplelog.backend.feature.home.web.dto.HomeResponse;
import com.meeplelog.backend.feature.user.dto.UserDetail;
import com.meeplelog.backend.feature.user.usecase.GetUserDetailUseCase;
import com.meeplelog.backend.security.CustomUserDetails;
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
import static org.mockito.Mockito.*;

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
        given(getMostPlayedGameSummariesUseCase.getMostPlayedGameSummariesByUser(1L, 10)).willReturn(mostPlayed);
        given(getRecentlyPlayedGameSummariesUseCase.getRecentlyPlayedGameSummariesByUser(1L, 10)).willReturn(recentlyPlayed);

        // when
        HomeResponse result = getHomeResponseUseCase.getHomeResponse(authentication);

        // then
        assertThat(result).isNotNull();
        verify(getUserDetailUseCase).getUserDetail(1L);
        verify(getMostPlayedGameSummariesUseCase).getMostPlayedGameSummariesByUser(1L, 10);
        verify(getRecentlyPlayedGameSummariesUseCase).getRecentlyPlayedGameSummariesByUser(1L, 10);
    }

    @Test
    @DisplayName("CustomUserDetails 타입의 인증 정보로 홈 응답을 생성한다")
    void getHomeResponse_customUserDetails() {
        // given
        CustomUserDetails customUserDetails = mock(CustomUserDetails.class);
        Authentication authentication = mock(Authentication.class);
        given(authentication.isAuthenticated()).willReturn(false);

        UserDetail userDetail = mock(UserDetail.class);
        List<CategorySummary> categories = List.of();
        List<GameSummary> mostPlayed = List.of();
        List<GameSummary> recentlyPlayed = List.of();

        given(securityUtil.getIdFromAuthentication(authentication)).willReturn(1L);
        given(getUserDetailUseCase.getUserDetail(1L)).willReturn(userDetail);
        given(getCategorySummariesUseCase.getCategorySummaries()).willReturn(categories);
        given(getMostPlayedGameSummariesUseCase.getMostPlayedGameSummariesByUser(1L, 10)).willReturn(mostPlayed);
        given(getRecentlyPlayedGameSummariesUseCase.getRecentlyPlayedGameSummariesByUser(1L, 10)).willReturn(recentlyPlayed);

        // when
        HomeResponse result = getHomeResponseUseCase.getHomeResponse(authentication);

        // then
        assertThat(result).isNotNull();
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
        given(getMostPlayedGameSummariesUseCase.getMostPlayedGameSummaries(10)).willReturn(mostPlayed);
        given(getRecentlyPlayedGameSummariesUseCase.getRecentlyPlayedGameSummaries(10)).willReturn(recentlyPlayed);

        // when
        HomeResponse result = getHomeResponseUseCase.getHomeResponse(authentication);

        // then
        assertThat(result).isNotNull();
        verify(getUserDetailUseCase, never()).getUserDetail(anyLong());
        verify(getMostPlayedGameSummariesUseCase).getMostPlayedGameSummaries(10);
        verify(getRecentlyPlayedGameSummariesUseCase).getRecentlyPlayedGameSummaries(10);
    }

    @Test
    @DisplayName("인증된 사용자는 사용자별 게임 통계를 받는다")
    void getHomeResponse_authenticatedUserGetsPersonalizedStats() {
        // given
        Authentication authentication = mock(Authentication.class);
        given(authentication.isAuthenticated()).willReturn(true);

        UserDetail userDetail = mock(UserDetail.class);
        given(securityUtil.getIdFromAuthentication(authentication)).willReturn(123L);
        given(getUserDetailUseCase.getUserDetail(123L)).willReturn(userDetail);
        given(getCategorySummariesUseCase.getCategorySummaries()).willReturn(List.of());
        given(getMostPlayedGameSummariesUseCase.getMostPlayedGameSummariesByUser(123L, 10)).willReturn(List.of());
        given(getRecentlyPlayedGameSummariesUseCase.getRecentlyPlayedGameSummariesByUser(123L, 10)).willReturn(List.of());

        // when
        getHomeResponseUseCase.getHomeResponse(authentication);

        // then
        verify(getMostPlayedGameSummariesUseCase).getMostPlayedGameSummariesByUser(123L, 10);
        verify(getRecentlyPlayedGameSummariesUseCase).getRecentlyPlayedGameSummariesByUser(123L, 10);
        verify(getMostPlayedGameSummariesUseCase, never()).getMostPlayedGameSummaries(anyInt());
        verify(getRecentlyPlayedGameSummariesUseCase, never()).getRecentlyPlayedGameSummaries(anyInt());
    }
}
