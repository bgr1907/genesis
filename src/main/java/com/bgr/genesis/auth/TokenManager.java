package com.bgr.genesis.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.Objects;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.stereotype.Service;

@Service
public class TokenManager {

    private static final String secretKey = "BuSecretKeyAslanimBuSecretKeyAslanimBuSecretKeyAslanim";

    public String generateToken(String email) {
        Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(secretKey),
            SignatureAlgorithm.HS256.getJcaName());

        return Jwts.builder()
            .setSubject(email)
            .setIssuer("bgr")
            .setIssuedAt(Date.from(Instant.now()))
            .setExpiration(Date.from(Instant.now().plus(1L, ChronoUnit.MINUTES)))
            .signWith(hmacKey)
            .compact();
    }

    public boolean tokenValidate(String token) {
        return Objects.nonNull(getUsernameFromToken(token)) && isExpired(token);
    }

    public String getUsernameFromToken(String token) {
        Claims claims = getClaims(token);
        return claims.getSubject();
    }

    public boolean isExpired(String token) {
        Claims claims = getClaims(token);
        return claims.getExpiration().after(Date.from(Instant.now()));
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token).getBody();
    }
}
