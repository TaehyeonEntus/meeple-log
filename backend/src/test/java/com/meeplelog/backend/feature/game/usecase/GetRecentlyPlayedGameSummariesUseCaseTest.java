package com.meeplelog.backend.feature.game.usecase;

import com.meeplelog.backend.feature.game.dto.GameSummary;
import com.meeplelog.backend.service.EventGameService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class GetRecentlyPlayedGameSummariesUseCaseTest {

    @Mock
    private EventGameService eventGameService;

    @InjectMocks
    private GetRecentlyPlayedGameSummariesUseCase getRecentlyPlayedGameSummariesUseCase;

    @Test
    @DisplayName("최근 플레이한 게임 요약 정보를 조회한다")
    void getRecentlyPlayedGameSummaries_success() {
        // given
        List<GameSummary> mockSummaries = List.of();
        given(eventGameService.getRecentlyPlayedGameSummaries(10)).willReturn(mockSummaries);

        // when
        List<GameSummary> result = getRecentlyPlayedGameSummariesUseCase.getRecentlyPlayedGameSummaries(10);

        // then
        assertThat(result).isNotNull();
        verify(eventGameService).getRecentlyPlayedGameSummaries(10);
    }

    @Test
    @DisplayName("특정 사용자의 최근 플레이한 게임 요약 정보를 조회한다")
    void getRecentlyPlayedGameSummariesByUser_success() {
        // given
        List<GameSummary> mockSummaries = List.of();
        given(eventGameService.getRecentlyPlayedGameSummariesByUser(1L, 5)).willReturn(mockSummaries);

        // when
        List<GameSummary> result = getRecentlyPlayedGameSummariesUseCase.getRecentlyPlayedGameSummariesByUser(1L, 5);

        // then
        assertThat(result).isNotNull();
        verify(eventGameService).getRecentlyPlayedGameSummariesByUser(1L, 5);
    }

    @Test
    @DisplayName("limit 값을 정확히 전달한다")
    void getRecentlyPlayedGameSummaries_withLimit() {
        // given
        List<GameSummary> mockSummaries = List.of();
        given(eventGameService.getRecentlyPlayedGameSummaries(20)).willReturn(mockSummaries);

        // when
        getRecentlyPlayedGameSummariesUseCase.getRecentlyPlayedGameSummaries(20);

        // then
        verify(eventGameService).getRecentlyPlayedGameSummaries(20);
    }
}
