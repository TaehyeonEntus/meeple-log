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
class GetMostPlayedGameSummariesUseCaseTest {

    @Mock
    private EventGameService eventGameService;

    @InjectMocks
    private GetMostPlayedGameSummariesUseCase getMostPlayedGameSummariesUseCase;

    @Test
    @DisplayName("가장 많이 플레이한 게임 요약 정보를 조회한다")
    void getMostPlayedGameSummaries_success() {
        // given
        List<GameSummary> mockSummaries = List.of();
        given(eventGameService.getMostPlayedGameSummaries(10)).willReturn(mockSummaries);

        // when
        List<GameSummary> result = getMostPlayedGameSummariesUseCase.getMostPlayedGameSummaries(10);

        // then
        assertThat(result).isNotNull();
        verify(eventGameService).getMostPlayedGameSummaries(10);
    }

    @Test
    @DisplayName("특정 사용자의 가장 많이 플레이한 게임 요약 정보를 조회한다")
    void getMostPlayedGameSummariesByUser_success() {
        // given
        List<GameSummary> mockSummaries = List.of();
        given(eventGameService.getMostPlayedGameSummariesByUser(1L, 5)).willReturn(mockSummaries);

        // when
        List<GameSummary> result = getMostPlayedGameSummariesUseCase.getMostPlayedGameSummariesByUser(1L, 5);

        // then
        assertThat(result).isNotNull();
        verify(eventGameService).getMostPlayedGameSummariesByUser(1L, 5);
    }

    @Test
    @DisplayName("특정 카테고리의 가장 많이 플레이한 게임 요약 정보를 조회한다")
    void getMostPlayedGameSummariesByCategory_success() {
        // given
        List<GameSummary> mockSummaries = List.of();
        given(eventGameService.getMostPlayedGameSummariesByCategory(1L, 10)).willReturn(mockSummaries);

        // when
        List<GameSummary> result = getMostPlayedGameSummariesUseCase.getMostPlayedGameSummariesByCategory(1L, 10);

        // then
        assertThat(result).isNotNull();
        verify(eventGameService).getMostPlayedGameSummariesByCategory(1L, 10);
    }

    @Test
    @DisplayName("limit 값을 정확히 전달한다")
    void getMostPlayedGameSummaries_withLimit() {
        // given
        List<GameSummary> mockSummaries = List.of();
        given(eventGameService.getMostPlayedGameSummaries(20)).willReturn(mockSummaries);

        // when
        getMostPlayedGameSummariesUseCase.getMostPlayedGameSummaries(20);

        // then
        verify(eventGameService).getMostPlayedGameSummaries(20);
    }
}
