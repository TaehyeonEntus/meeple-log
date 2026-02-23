package com.meeplelog.backend.usecase.addPlayer;

import com.meeplelog.backend.domain.Player;
import com.meeplelog.backend.exception.DuplicateNameException;
import com.meeplelog.backend.exception.DuplicateUsernameException;
import com.meeplelog.backend.exception.PasswordMismatchException;
import com.meeplelog.backend.service.PlayerService;
import com.meeplelog.backend.usecase.addPlayer.dto.AddPlayerRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AddPlayerUseCase {
    //todo 비밀번호 인코딩 해야댐 시큐리티 추가하면

    private final PlayerService playerService;

    @Transactional
    public Player addPlayer(AddPlayerRequest request){
        String name = request.name();
        String username = request.username();
        String password = request.password();
        String passwordConfirm = request.passwordConfirm();

        validateNameUniqueness(name);
        validateUsernameUniqueness(username);
        validatePasswordMatch(password, passwordConfirm);

        return playerService.add(Player.of(name,username,password));
    }

    private void validateNameUniqueness(String name){
        if(playerService.existsByName(name))
            throw new DuplicateNameException("이미 존재하는 이름입니다.");
    }

    private void validateUsernameUniqueness(String username){
        if(playerService.existsByUsername(username))
            throw new DuplicateUsernameException("이미 존재하는 아이디입니다.");
    }

    private void validatePasswordMatch(String password, String passwordConfirm){
        if(!password.equals(passwordConfirm))
            throw new PasswordMismatchException("비밀번호가 일치하지 않습니다.");
    }
}
