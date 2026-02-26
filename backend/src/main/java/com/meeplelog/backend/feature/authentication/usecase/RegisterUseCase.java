package com.meeplelog.backend.feature.authentication.usecase;

import com.meeplelog.backend.domain.User;
import com.meeplelog.backend.exception.DuplicateNameException;
import com.meeplelog.backend.exception.DuplicateUsernameException;
import com.meeplelog.backend.exception.PasswordMismatchException;
import com.meeplelog.backend.service.UserService;
import com.meeplelog.backend.feature.authentication.web.dto.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RegisterUseCase {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User register(RegisterRequest request){
        String username = request.username();
        String name = request.name();
        String password = request.password();
        String passwordConfirm = request.passwordConfirm();

        validateNameUniqueness(name);
        validateUsernameUniqueness(username);
        validatePasswordMatch(password, passwordConfirm);

        return userService.add(User.of(name,username,passwordEncoder.encode(password),null));
    }

    private void validateNameUniqueness(String name){
        if(userService.existsByName(name))
            throw new DuplicateNameException("이미 존재하는 이름입니다.");
    }

    private void validateUsernameUniqueness(String username){
        if(userService.existsByUsername(username))
            throw new DuplicateUsernameException("이미 존재하는 아이디입니다.");
    }

    private void validatePasswordMatch(String password, String passwordConfirm){
        if(!password.equals(passwordConfirm))
            throw new PasswordMismatchException("비밀번호가 일치하지 않습니다.");
    }
}
