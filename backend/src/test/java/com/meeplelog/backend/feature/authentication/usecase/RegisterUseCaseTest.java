package com.meeplelog.backend.feature.authentication.usecase;

import com.meeplelog.backend.domain.User;
import com.meeplelog.backend.exception.DuplicateNameException;
import com.meeplelog.backend.exception.DuplicateUsernameException;
import com.meeplelog.backend.exception.PasswordMismatchException;
import com.meeplelog.backend.feature.authentication.web.dto.RegisterRequest;
import com.meeplelog.backend.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RegisterUseCaseTest {

    @Mock
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private RegisterUseCase registerUseCase;

    private RegisterRequest validRequest;

    @BeforeEach
    void setUp() {
        validRequest = new RegisterRequest("testUser", "테스트유저", "password123", "password123");
    }

    @Test
    @DisplayName("정상적인 회원가입 요청 시 사용자가 생성된다")
    void register_success() {
        // given
        given(userService.existsByName(anyString())).willReturn(false);
        given(userService.existsByUsername(anyString())).willReturn(false);
        given(passwordEncoder.encode(anyString())).willReturn("encodedPassword");
        given(userService.add(any(User.class))).willAnswer(invocation -> invocation.getArgument(0));

        // when
        User result = registerUseCase.register(validRequest);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("테스트유저");
        assertThat(result.getUsername()).isEqualTo("testUser");
        verify(userService).add(any(User.class));
    }

    @Test
    @DisplayName("중복된 이름으로 회원가입 시 DuplicateNameException이 발생한다")
    void register_duplicateName_throwsException() {
        // given
        given(userService.existsByName("테스트유저")).willReturn(true);

        // when & then
        assertThatThrownBy(() -> registerUseCase.register(validRequest))
                .isInstanceOf(DuplicateNameException.class)
                .hasMessage("이미 존재하는 이름입니다.");
    }

    @Test
    @DisplayName("중복된 아이디로 회원가입 시 DuplicateUsernameException이 발생한다")
    void register_duplicateUsername_throwsException() {
        // given
        given(userService.existsByName(anyString())).willReturn(false);
        given(userService.existsByUsername("testUser")).willReturn(true);

        // when & then
        assertThatThrownBy(() -> registerUseCase.register(validRequest))
                .isInstanceOf(DuplicateUsernameException.class)
                .hasMessage("이미 존재하는 아이디입니다.");
    }

    @Test
    @DisplayName("비밀번호가 일치하지 않으면 PasswordMismatchException이 발생한다")
    void register_passwordMismatch_throwsException() {
        // given
        RegisterRequest mismatchRequest = new RegisterRequest("testUser", "테스트유저", "password123", "password456");
        given(userService.existsByName(anyString())).willReturn(false);
        given(userService.existsByUsername(anyString())).willReturn(false);

        // when & then
        assertThatThrownBy(() -> registerUseCase.register(mismatchRequest))
                .isInstanceOf(PasswordMismatchException.class)
                .hasMessage("비밀번호가 일치하지 않습니다.");
    }

    @Test
    @DisplayName("비밀번호는 암호화되어 저장된다")
    void register_passwordEncoded() {
        // given
        given(userService.existsByName(anyString())).willReturn(false);
        given(userService.existsByUsername(anyString())).willReturn(false);
        given(passwordEncoder.encode("password123")).willReturn("encodedPassword123");
        given(userService.add(any(User.class))).willAnswer(invocation -> invocation.getArgument(0));

        // when
        User result = registerUseCase.register(validRequest);

        // then
        verify(passwordEncoder).encode("password123");
        verify(userService).add(any(User.class));
    }
}
