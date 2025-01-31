package com.example.dynamic_crud_rest_api.base.config.security;

import com.example.dynamic_crud_rest_api.base.exception.ApiException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtProvider {
    @Value("${jwt.access-token.key}")
    private String accessTokenKey;

    @Value("${jwt.access-token.expiration}")
    private Long accessTokenExpiration;

    @Value("${jwt.refresh-token.key}")
    private String refreshTokenKey;

    @Value("${jwt.refresh-token.expiration}")
    private Long refreshTokenExpiration;

    @Value("${spring.application.name}")
    private String applicationName;

    public TokenDTO generateTokens(String source, Long userId){
        Date accessTokenExpiration = new Date(System.currentTimeMillis() + this.accessTokenExpiration);
        String accessToken = generate(source, accessTokenExpiration, userId, getAccessTokenKey());
        Date refreshTokenExpiration = new Date(System.currentTimeMillis() + this.refreshTokenExpiration);
        String refreshToken = generate(source, refreshTokenExpiration, userId, getRefreshTokenKey());
        return TokenDTO.of(accessToken, accessTokenExpiration, refreshToken, refreshTokenExpiration);
    }

    private String generate(String source, Date expiration, Long userId, SecretKey secretKey){
        return Jwts.builder()
                .subject(source)
                .issuer(applicationName)
                .expiration(expiration)
                .issuedAt(new Date())
                .claim(TokenDTO._userId, userId)
                .signWith(secretKey)
                .compact();
    }

    public Claims extractAndGetClaims(String token, boolean isAccessToken) {
        SecretKey secretKey=isAccessToken ? getAccessTokenKey() : getRefreshTokenKey();
        Claims claims = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        if (claims.getExpiration().before(new Date())) {
            throw new ApiException(isAccessToken ? "Access token expired" : "Refresh token expired");
        }
        return claims;
    }

    private SecretKey getAccessTokenKey(){
        return Keys.hmacShaKeyFor(accessTokenKey.getBytes());
    }

    private SecretKey getRefreshTokenKey(){
        return Keys.hmacShaKeyFor(refreshTokenKey.getBytes());
    }
}
