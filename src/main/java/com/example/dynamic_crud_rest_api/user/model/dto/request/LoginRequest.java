package com.example.dynamic_crud_rest_api.user.model.dto.request;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank
        String login,

        @NotBlank
        String password
) {
}
