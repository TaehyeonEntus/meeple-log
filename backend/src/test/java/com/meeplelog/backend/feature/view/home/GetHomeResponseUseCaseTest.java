package com.meeplelog.backend.feature.view.home;

import com.meeplelog.backend.feature.category.dto.CategorySummary;
import com.meeplelog.backend.feature.category.usecase.GetCategorySummariesUseCase;
import com.meeplelog.backend.feature.game.dto.GameSummary;
import com.meeplelog.backend.feature.game.usecase.GetMostPlayedGameSummariesUseCase;
import com.meeplelog.backend.feature.game.usecase.GetRecentlyPlayedGameSummariesUseCase;
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
    @DisplayName("인증된 사용자의 홈 화면 정보를 조회한다")
    void getHomeResponse_authenticated() {
        // given
        Authentication authentication = mock(Authentication.class);
        given(authentication.isAuthenticated()).willReturn(true);
        long userId = 1L;
        given(securityUtil.getIdFromAuthentication(authentication)).willReturn(userId);

        UserDetail userDetail = new UserDetail(userId, "Test User", "http://image.url");
        given(getUserDetailUseCase.getUserDetail(userId)).willReturn(userDetail);

        List<CategorySummary> categories = List.of(new CategorySummary(1L, "Strategy"));
        given(getCategorySummariesUseCase.getCategorySummaries()).willReturn(categories);

        List<GameSummary> mostPlayed = List.of(new GameSummary(1L, "Catan", "http://image.url"));
        given(getMostPlayedGameSummariesUseCase.getMostPlayedGameSummaries(userId, null, 10)).willReturn(mostPlayed);

        List<GameSummary> recentlyPlayed = List.of(new GameSummary(1L, "Catan", "http://image.url"));
        given(getRecentlyPlayedGameSummariesUseCase.getRecentlyPlayedGameSummaries(userId, null, 10)).willReturn(recentlyPlayed);

        // when
        HomeResponse result = getHomeResponseUseCase.getHomeResponse(authentication);

        // then
        assertThat(result).isNotNull();
        assertThat(result.user()).isEqualTo(userDetail);
        assertThat(result.categories()).isEqualTo(categories);
        assertThat(result.mostPlayedGames()).isEqualTo(mostPlayed);
        assertThat(result.recentlyPlayedGames()).isEqualTo(recentlyPlayed);
    }
}
