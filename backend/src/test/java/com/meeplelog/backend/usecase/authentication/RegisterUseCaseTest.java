package com.meeplelog.backend.usecase.authentication;

import com.meeplelog.backend.domain.Player;
import com.meeplelog.backend.exception.DuplicateNameException;
import com.meeplelog.backend.exception.DuplicateUsernameException;
import com.meeplelog.backend.exception.PasswordMismatchException;
import com.meeplelog.backend.service.PlayerService;
import com.meeplelog.backend.usecase.authentication.dto.RegisterRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegisterUseCaseTest {

    @InjectMocks
    private RegisterUseCase registerUseCase;

    @Mock
    private PlayerService playerService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("회원가입 성공")
    void register_success() {
        // given
        RegisterRequest request = new RegisterRequest("testuser", "Test Name", "password123", "password123");
        when(playerService.existsByName(request.name())).thenReturn(false);
        when(playerService.existsByUsername(request.username())).thenReturn(false);
        when(passwordEncoder.encode(request.password())).thenReturn("encodedPassword");
        when(playerService.add(any(Player.class))).thenAnswer(returnsFirstArg());

        // when
        Player player = registerUseCase.register(request);

        // then
        Assertions.assertNotNull(player);
        Assertions.assertEquals("Test Name", player.getName());
        Assertions.assertEquals("testuser", player.getUsername());
        Assertions.assertEquals("encodedPassword", player.getPassword());
    }

    @Test
    @DisplayName("이름 중복 시 예외 발생")
    void register_duplicateName() {
        // given
        RegisterRequest request = new RegisterRequest("testuser", "Existing Name", "password123", "password123");
        when(playerService.existsByName(request.name())).thenReturn(true);

        // when & then
        Assertions.assertThrows(DuplicateNameException.class, () -> registerUseCase.register(request));
    }

    @Test
    @DisplayName("아이디 중복 시 예외 발생")
    void register_duplicateUsername() {
        // given
        RegisterRequest request = new RegisterRequest("existinguser", "Test Name", "password123", "password123");
        when(playerService.existsByName(request.name())).thenReturn(false);
        when(playerService.existsByUsername(request.username())).thenReturn(true);

        // when & then
        Assertions.assertThrows(DuplicateUsernameException.class, () -> registerUseCase.register(request));
    }

    @Test
    @DisplayName("비밀번호 불일치 시 예외 발생")
    void register_passwordMismatch() {
        // given
        RegisterRequest request = new RegisterRequest("testuser", "Test Name", "password123", "wrongpassword");
        when(playerService.existsByName(request.name())).thenReturn(false);
        when(playerService.existsByUsername(request.username())).thenReturn(false);

        // when & then
        Assertions.assertThrows(PasswordMismatchException.class, () -> registerUseCase.register(request));
    }
}
