package com.bgr.genesis.repository;

import com.bgr.genesis.entity.Users;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Integer> {

    Optional<Users> findById(Integer id);
    Optional<Users> findByRefreshToken(String refreshToken);
    Optional<Users> findByEmail(String email);

}
