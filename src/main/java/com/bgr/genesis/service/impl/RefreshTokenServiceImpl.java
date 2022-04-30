package com.bgr.genesis.service.impl;

import com.bgr.genesis.entity.RefreshToken;
import com.bgr.genesis.entity.Users;
import com.bgr.genesis.exception.TokenRefreshException;
import com.bgr.genesis.repository.RefreshTokenRepository;
import com.bgr.genesis.repository.UserRepository;
import com.bgr.genesis.service.RefreshTokenService;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import javax.persistence.PersistenceException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    @Override
    public RefreshToken createRefreshToken(String email) {

        Optional<Users> user = userRepository.findByEmail(email);

        if (user.isPresent()) {
            RefreshToken refreshToken = RefreshToken.builder()
                .user(user.get())
                .expiryDate(Instant.now().plusMillis(120000))
                .token(UUID.randomUUID().toString())
                .build();

            return refreshTokenRepository.save(refreshToken);
        }
        throw new PersistenceException("User not found in DB");
    }

    @Override
    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new TokenRefreshException(token.getToken(), "Refresh token was expired. Please make a new sign in request.");
        }
        return token;
    }

    @Override
    @Transactional
    public int deleteByUserId(Integer userId) {

        Optional<Users> user = userRepository.findById(userId);

        if(user.isPresent()) {
            return refreshTokenRepository.deleteByUser(user.get());
        }
        throw new PersistenceException("No user found to be deleted");
    }
}
