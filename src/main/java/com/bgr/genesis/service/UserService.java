package com.bgr.genesis.service;

import com.bgr.genesis.entity.Users;
import java.util.Optional;

public interface UserService {

    Users save(Users user);
    Optional<Users> findByEmail(String email);
}
