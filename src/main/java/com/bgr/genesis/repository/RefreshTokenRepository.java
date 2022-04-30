package com.bgr.genesis.repository;

import com.bgr.genesis.entity.RefreshToken;
import com.bgr.genesis.entity.Users;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {

    Optional<RefreshToken> findById(Integer id);
    Optional<RefreshToken> findByToken(String token);
    int deleteByUser(Users user);

}
