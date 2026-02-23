package com.meeplelog.backend.usecase.addPlayer;

import com.meeplelog.backend.domain.Player;
import com.meeplelog.backend.exception.DuplicateNameException;
import com.meeplelog.backend.exception.DuplicateUsernameException;
import com.meeplelog.backend.exception.PasswordMismatchException;
import com.meeplelog.backend.service.PlayerService;
import com.meeplelog.backend.usecase.addPlayer.dto.AddPlayerRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AddPlayerUseCaseTest {

    @InjectMocks
    private AddPlayerUseCase addPlayerUseCase;

    @Mock
    private PlayerService playerService;

    @Test
    @DisplayName("플레이어 추가 성공")
    void addPlayer_success() {
        // given
        AddPlayerRequest request = new AddPlayerRequest("testUser", "테스터", "password123", "password123");

        when(playerService.add(any())).thenAnswer(returnsFirstArg());

        // when
        Player player = addPlayerUseCase.addPlayer(request);

        // then
        Assertions.assertEquals("테스터", player.getName());
        Assertions.assertEquals("testUser", player.getUsername());
        Assertions.assertEquals("password123", player.getPassword());
    }

    @Test
    @DisplayName("이름 중복 시 예외 발생")
    void addPlayer_duplicateName() {
        // given
        AddPlayerRequest request = new AddPlayerRequest("testUser", "테스터", "password123", "password123");
        when(playerService.existsByName(request.name())).thenReturn(true);

        // when & then
        Assertions.assertThrows(DuplicateNameException.class, () -> addPlayerUseCase.addPlayer(request));
    }

    @Test
    @DisplayName("아이디 중복 시 예외 발생")
    void addPlayer_duplicateUsername() {
        // given
        AddPlayerRequest request = new AddPlayerRequest("testUser", "테스터", "password123", "password123");
        when(playerService.existsByUsername(request.username())).thenReturn(true);

        // when & then
        Assertions.assertThrows(DuplicateUsernameException.class, () -> addPlayerUseCase.addPlayer(request));
    }

    @Test
    @DisplayName("비밀번호 불일치 시 예외 발생")
    void addPlayer_passwordMismatch() {
        // given
        AddPlayerRequest request = new AddPlayerRequest("testUser", "테스터", "password123", "password456");

        // when & then
        Assertions.assertThrows(PasswordMismatchException.class, () -> addPlayerUseCase.addPlayer(request));
    }
}
