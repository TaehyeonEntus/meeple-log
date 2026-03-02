package com.meeplelog.backend.feature.game.usecase;

import com.meeplelog.backend.domain.Game;
import com.meeplelog.backend.exception.DuplicateNameException;
import com.meeplelog.backend.feature.game.web.dto.AddGameRequest;
import com.meeplelog.backend.service.GameService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AddGameUseCaseTest {

    @Mock
    private GameService gameService;

    @InjectMocks
    private AddGameUseCase addGameUseCase;

    @Test
    @DisplayName("새로운 게임을 추가한다")
    void addGame_success() {
        // given
        AddGameRequest request = new AddGameRequest("Catan", "http://image.url");
        Game game = Game.of("Catan", "http://image.url");
        
        given(gameService.existsByName("Catan")).willReturn(false);
        given(gameService.add(any(Game.class))).willReturn(game);

        // when
        Game result = addGameUseCase.addGame(request);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Catan");
        verify(gameService).existsByName("Catan");
        verify(gameService).add(any(Game.class));
    }

    @Test
    @DisplayName("이미 존재하는 이름의 게임을 추가하면 예외가 발생한다")
    void addGame_duplicateName_throwsException() {
        // given
        AddGameRequest request = new AddGameRequest("Catan", "http://image.url");
        given(gameService.existsByName("Catan")).willReturn(true);

        // when & then
        assertThatThrownBy(() -> addGameUseCase.addGame(request))
                .isInstanceOf(DuplicateNameException.class)
                .hasMessage("이미 존재하는 이름입니다.");
    }
}
