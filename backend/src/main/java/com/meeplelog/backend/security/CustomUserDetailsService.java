package com.meeplelog.backend.security;

import com.meeplelog.backend.domain.User;
import com.meeplelog.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserService userService;

    @Override
    @NullMarked
    public UserDetails loadUserByUsername(String username){
        User user = userService.getByUsername(username);
        return new CustomUserDetails(user);
    }

    public UserDetails loadUserById(long id){
        User user = userService.get(id);
        return new CustomUserDetails(user);
    }
}