package com.meeplelog.backend.security;

import com.meeplelog.backend.domain.Player;
import com.meeplelog.backend.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final PlayerService playerService;

    @Override
    @NullMarked
    public UserDetails loadUserByUsername(String username){
        Player player = playerService.getByUsername(username);
        return new CustomUserDetails(player);
    }

    public UserDetails loadUserById(long id){
        Player player = playerService.get(id);
        return new CustomUserDetails(player);
    }
}