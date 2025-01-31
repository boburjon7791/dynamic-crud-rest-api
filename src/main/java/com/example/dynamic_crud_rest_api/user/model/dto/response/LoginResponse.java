package com.example.dynamic_crud_rest_api.user.model.dto.response;

import com.example.dynamic_crud_rest_api.base.config.security.TokenDTO;

public record LoginResponse(
        UserResponse user,
        TokenDTO tokens
) {
    public static LoginResponse of(UserResponse userResponse, TokenDTO tokenDTO) {
        return new LoginResponse(userResponse, tokenDTO);
    }
}
