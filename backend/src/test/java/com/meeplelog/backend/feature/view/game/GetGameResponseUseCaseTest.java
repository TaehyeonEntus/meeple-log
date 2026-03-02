package com.meeplelog.backend.feature.view.game;

import com.meeplelog.backend.feature.game.dto.GameDetail;
import com.meeplelog.backend.feature.game.usecase.GetGameDetailUseCase;
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
class GetGameResponseUseCaseTest {

    @Mock
    private GetGameDetailUseCase getGameDetailUseCase;

    @InjectMocks
    private GetGameResponseUseCase getGameResponseUseCase;

    @Test
    @DisplayName("게임 응답 정보를 조회한다")
    void getGameResponse_success() {
        // given
        Long gameId = 1L;
        GameDetail gameDetail = new GameDetail(gameId, "Catan", "http://image.url");
        given(getGameDetailUseCase.getGameDetail(gameId)).willReturn(gameDetail);

        // when
        GameResponse result = getGameResponseUseCase.getGameResponse(gameId);

        // then
        assertThat(result).isNotNull();
        assertThat(result.gameDetail().name()).isEqualTo("Catan");
        verify(getGameDetailUseCase).getGameDetail(gameId);
    }
}
