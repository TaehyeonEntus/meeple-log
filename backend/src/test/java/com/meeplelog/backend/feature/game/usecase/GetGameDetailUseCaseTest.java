package com.meeplelog.backend.feature.game.usecase;

import com.meeplelog.backend.domain.Game;
import com.meeplelog.backend.feature.game.dto.GameDetail;
import com.meeplelog.backend.service.GameService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class GetGameDetailUseCaseTest {

    @Mock
    private GameService gameService;

    @InjectMocks
    private GetGameDetailUseCase getGameDetailUseCase;

    @Test
    @DisplayName("게임 상세 정보를 조회한다")
    void getGameDetail_success() {
        // given
        long gameId = 1L;
        Game game = Game.of("Catan", "http://image.url");
        given(gameService.get(gameId)).willReturn(game);

        // when
        GameDetail result = getGameDetailUseCase.getGameDetail(gameId);

        // then
        assertThat(result).isNotNull();
        assertThat(result.name()).isEqualTo("Catan");
        verify(gameService).get(gameId);
    }
}
