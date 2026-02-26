package com.meeplelog.backend.feature.game.usecase;

import com.meeplelog.backend.domain.Game;
import com.meeplelog.backend.exception.DuplicateNameException;
import com.meeplelog.backend.feature.game.web.dto.AddGameRequest;
import com.meeplelog.backend.service.GameService;
import org.junit.jupiter.api.BeforeEach;
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

    private AddGameRequest validRequest;

    @BeforeEach
    void setUp() {
        validRequest = new AddGameRequest("스플렌더", "http://image.url/splendor.jpg");
    }

    @Test
    @DisplayName("정상적인 게임 추가 요청 시 게임이 생성된다")
    void addGame_success() {
        // given
        given(gameService.existsByName("스플렌더")).willReturn(false);
        given(gameService.add(any(Game.class))).willAnswer(invocation -> invocation.getArgument(0));

        // when
        Game result = addGameUseCase.addGame(validRequest);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("스플렌더");
        assertThat(result.getImageUrl()).isEqualTo("http://image.url/splendor.jpg");
        verify(gameService).add(any(Game.class));
    }

    @Test
    @DisplayName("중복된 게임 이름으로 추가 시 DuplicateNameException이 발생한다")
    void addGame_duplicateName_throwsException() {
        // given
        given(gameService.existsByName("스플렌더")).willReturn(true);

        // when & then
        assertThatThrownBy(() -> addGameUseCase.addGame(validRequest))
                .isInstanceOf(DuplicateNameException.class)
                .hasMessage("이미 존재하는 이름입니다.");
    }

    @Test
    @DisplayName("이미지 URL 없이 게임을 추가할 수 있다")
    void addGame_withoutImageUrl() {
        // given
        AddGameRequest requestWithoutImage = new AddGameRequest("카탄", null);
        given(gameService.existsByName("카탄")).willReturn(false);
        given(gameService.add(any(Game.class))).willAnswer(invocation -> invocation.getArgument(0));

        // when
        Game result = addGameUseCase.addGame(requestWithoutImage);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("카탄");
        assertThat(result.getImageUrl()).isNull();
    }

    @Test
    @DisplayName("게임 추가 전 이름 중복 검증이 수행된다")
    void addGame_validatesNameUniqueness() {
        // given
        given(gameService.existsByName("스플렌더")).willReturn(false);
        given(gameService.add(any(Game.class))).willAnswer(invocation -> invocation.getArgument(0));

        // when
        addGameUseCase.addGame(validRequest);

        // then
        verify(gameService).existsByName("스플렌더");
    }
}
