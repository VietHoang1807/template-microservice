package com.kk.sso.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import com.kk.sso.entity.UserEntity;
import com.kk.sso.model.RecordToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.lang.Function;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtService {

    @Value("${kk.jwt.secret}")
    private String jwtSecret;
    @Value("${kk.jwt.secret-time}")
    private Integer jwtSecretTime;
    @Value("${kk.jwt.secret-refresh}")
    private String jwtSecretRefresh;
    @Value("${kk.jwt.secret-refresh-time}")
    private Integer jwtSecretRefreshTime;

    private final RedisService redisService;

    public RecordToken generatingToken(UserEntity u) {
        String generatedToken = generateToken(u);
        String generatedTokenRefresh = generateTokenRefresh(u);
        return RecordToken.builder()
                .token(generatedToken)
                .tokenRefresh(generatedTokenRefresh)
                .build();
    }

    public String extractUsername(String token) {
        // Extract username from JWT token
        return extractClaim(token, Claims::getSubject, jwtSecret);
    }

    public String extractUsernameWithRefresh(String token) {
        // Extract username from JWT token
        return extractClaim(token, Claims::getSubject, jwtSecretRefresh);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver, String secret) {
        final Claims claims = extractAllClaims(token, secret);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserEntity uEntity) {
        String token = generateToken(new HashMap<>(), uEntity, jwtSecret, jwtSecretTime); // 1 day
        redisService.set(uEntity.getUsername() + " :: TOKEN", token, 1L, TimeUnit.DAYS);
        return token;
    }

    public String generateTokenRefresh(UserEntity uEntity) {
        String tokenRefresh = generateToken(new HashMap<>(), uEntity, jwtSecretRefresh, jwtSecretRefreshTime); // 7 days
        redisService.set(uEntity.getUsername() + " :: TOKEN REFRESH", tokenRefresh, 7L, TimeUnit.DAYS);
        return tokenRefresh;
    }

    @SuppressWarnings("deprecation")
    public String generateToken(
            Map<String, Object> extractClaims,
            UserEntity uEntity,
            String secret,
            Integer expire) {
        return Jwts.builder().claims(extractClaims)
                .subject(uEntity.getUsername())
                .issuer(uEntity.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expire))
                .signWith(getSignInKey(secret), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserEntity userEntity) {
        final String username = extractUsername(token);
        return Objects.equals(username, userEntity.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration, jwtSecret);
    }

    @SuppressWarnings("deprecation")
    private Claims extractAllClaims(String token, String secret) {
        return Jwts.parser()
                .setSigningKey(getSignInKey(secret))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey(String secret) {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
