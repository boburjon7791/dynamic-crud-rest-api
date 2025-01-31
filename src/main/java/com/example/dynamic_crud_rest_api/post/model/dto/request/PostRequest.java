package com.example.dynamic_crud_rest_api.post.model.dto.request;

import jakarta.validation.constraints.NotBlank;

public record PostRequest(
        @NotBlank
        String title,

        @NotBlank
        String body
) {
}
