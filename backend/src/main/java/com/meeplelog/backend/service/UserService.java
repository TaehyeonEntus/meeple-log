package com.meeplelog.backend.service;

import com.meeplelog.backend.domain.User;
import com.meeplelog.backend.infra.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User add(User user){
        return userRepository.save(user);
    }

    public User get(long userId){
        return userRepository.findById(userId).orElseThrow();
    }

    public boolean existsByName(String name){
        return userRepository.existsByName(name);
    }

    public boolean existsByUsername(String username){
        return userRepository.existsByUsername(username);
    }

    public User getByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow();
    }
}
