package com.bgr.genesis.converter;

import com.bgr.genesis.entity.Users;
import com.bgr.genesis.request.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserConverter {

    private final BCryptPasswordEncoder passwordEncoder;

    public Users RegisterRequestToUsersEntity(RegisterRequest registerRequest) {
        return Users.builder()
            .email(registerRequest.getEmail())
            .password(passwordEncoder.encode(registerRequest.getPassword()))
            .name(registerRequest.getName())
            .lastname(registerRequest.getLastname())
            .build();
    }
}
