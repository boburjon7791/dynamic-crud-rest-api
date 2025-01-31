package com.example.dynamic_crud_rest_api.base.config.security;

import java.util.Date;

public record TokenDTO(String accessToken, Date accessTokenExpiration, String refreshToken, Date refreshTokenExpiration) {
    public static final String _userId = "userId";

    public static TokenDTO of(String accessToken, Date accessTokenExpiration, String refreshToken, Date refreshTokenExpiration) {
        return new TokenDTO(accessToken, accessTokenExpiration, refreshToken, refreshTokenExpiration);
    }
}
