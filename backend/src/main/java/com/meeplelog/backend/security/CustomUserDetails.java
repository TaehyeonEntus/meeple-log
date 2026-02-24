package com.meeplelog.backend.security;

import com.meeplelog.backend.domain.Player;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {
    private final Player player;

    @Override
    @NonNull
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // For now, we are not using roles.
        return Collections.emptyList();
    }

    public long getId() {
        return player.getId();
    }

    public String getName() {
        return player.getName();
    }

    @Override
    @NonNull
    public String getUsername() {
        return player.getUsername();
    }

    @Override
    public String getPassword() {
        return player.getPassword();
    }
}
