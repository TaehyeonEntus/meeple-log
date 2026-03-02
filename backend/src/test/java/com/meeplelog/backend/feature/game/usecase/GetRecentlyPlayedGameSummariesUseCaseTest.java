package com.meeplelog.backend.feature.game.usecase;

import com.meeplelog.backend.feature.game.dto.GameSummary;
import com.meeplelog.backend.service.GameService;
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
    private GameService gameService;

    @InjectMocks
    private GetRecentlyPlayedGameSummariesUseCase getRecentlyPlayedGameSummariesUseCase;

    @Test
    @DisplayName("최근 플레이한 게임 요약 정보를 조회한다")
    void getRecentlyPlayedGameSummaries_success() {
        // given
        Long userId = 1L;
        Long categoryId = 2L;
        int limit = 5;
        List<GameSummary> summaries = List.of(new GameSummary(1L, "Catan", "http://image.url"));
        given(gameService.getRecentlyPlayedGameSummaries(userId, categoryId, limit)).willReturn(summaries);

        // when
        List<GameSummary> result = getRecentlyPlayedGameSummariesUseCase.getRecentlyPlayedGameSummaries(userId, categoryId, limit);

        // then
        assertThat(result).hasSize(1);
        assertThat(result.getFirst().name()).isEqualTo("Catan");
        verify(gameService).getRecentlyPlayedGameSummaries(userId, categoryId, limit);
    }
}
