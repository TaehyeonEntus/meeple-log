package com.meeplelog.backend.usecase.addGame;

import com.meeplelog.backend.domain.Game;
import com.meeplelog.backend.exception.DuplicateNameException;
import com.meeplelog.backend.service.GameService;
import com.meeplelog.backend.usecase.addGame.dto.AddGameRequest;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AddGameUseCaseTest {

    @InjectMocks
    private AddGameUseCase addGameUseCase;

    @Mock
    private GameService gameService;

    @Test
    @DisplayName("게임 추가 성공")
    void addGame_success() {
        // given
        AddGameRequest request = new AddGameRequest("Test Game");
        when(gameService.add(any())).thenAnswer(returnsFirstArg());

        // when
        Game game = addGameUseCase.addGame(request);

        // then
        Assertions.assertEquals("Test Game", game.getName());
    }

    @Test
    @DisplayName("게임 이름 중복 시 예외 발생")
    void addGame_duplicateName() {
        // given
        AddGameRequest request = new AddGameRequest("Existing Game");
        when(gameService.existsByName(request.name())).thenReturn(true);

        // when & then
        Assertions.assertThrows(DuplicateNameException.class, () -> addGameUseCase.addGame(request));
    }
}
