package com.bgr.genesis.api;

import com.bgr.genesis.converter.UserConverter;
import com.bgr.genesis.entity.RefreshToken;
import com.bgr.genesis.entity.Users;
import com.bgr.genesis.exception.TokenRefreshException;
import com.bgr.genesis.repository.UserRepository;
import com.bgr.genesis.request.RefreshTokenRequest;
import com.bgr.genesis.request.RegisterRequest;
import com.bgr.genesis.response.LoginResponse;
import com.bgr.genesis.response.RefreshTokenResponse;
import com.bgr.genesis.auth.TokenManager;
import com.bgr.genesis.request.LoginRequest;
import com.bgr.genesis.service.RefreshTokenService;
import com.bgr.genesis.service.UserService;
import java.util.Objects;
import java.util.Optional;
import javax.persistence.PersistenceException;
import lombok.AllArgsConstructor;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@Slf4j
@AllArgsConstructor
public class AuthController {

    private AuthenticationManager authenticationManager;
    private TokenManager tokenManager;
    private RefreshTokenService refreshTokenService;
    private UserService userService;
    private UserConverter userConverter;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        try {
            RefreshToken refreshToken;
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

            refreshToken = refreshTokenService.createRefreshToken(loginRequest.getEmail());

            return ResponseEntity.ok(
                LoginResponse.builder().accessToken(tokenManager.generateToken(loginRequest.getEmail()))
                    .refreshToken(refreshToken.getToken()).build());

        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {
        try {
            Users user = userConverter.RegisterRequestToUsersEntity(registerRequest);
            userService.save(user);
            return ResponseEntity.ok("Saved operation successfully");
        } catch (PersistenceException persistenceException) {
            throw persistenceException;
        }
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<RefreshTokenResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        String requestRefreshToken = refreshTokenRequest.getRefreshToken();
        return refreshTokenService.findByToken(requestRefreshToken)
            .map(refreshTokenService::verifyExpiration)
            .map(RefreshToken::getUser)
            .map(user -> {
                String token = tokenManager.generateToken(user.getEmail());
                return ResponseEntity.ok(new RefreshTokenResponse(token, requestRefreshToken));
            }).orElseThrow(() -> new TokenRefreshException(requestRefreshToken, "Refresh token is not in database.!"));
    }
}
