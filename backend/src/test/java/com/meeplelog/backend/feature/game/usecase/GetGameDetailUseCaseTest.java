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

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class GetGameDetailUseCaseTest {

    @Mock
    private GameService gameService;

    @InjectMocks
    private GetGameDetailUseCase getGameDetailUseCase;

    @Test
    @DisplayName("게임 ID로 게임 상세 정보를 조회한다")
    void getGameDetail_success() {
        // given
        Game game = Game.of("스플렌더", "http://image.url");
        given(gameService.get(1L)).willReturn(game);

        // when
        GameDetail result = getGameDetailUseCase.getGameDetail(1L);

        // then
        assertThat(result).isNotNull();
        verify(gameService).get(1L);
    }

    @Test
    @DisplayName("존재하지 않는 게임 ID로 조회 시 예외가 발생한다")
    void getGameDetail_notFound_throwsException() {
        // given
        given(gameService.get(999L)).willThrow(NoSuchElementException.class);

        // when & then
        assertThatThrownBy(() -> getGameDetailUseCase.getGameDetail(999L))
                .isInstanceOf(NoSuchElementException.class);
    }
}
