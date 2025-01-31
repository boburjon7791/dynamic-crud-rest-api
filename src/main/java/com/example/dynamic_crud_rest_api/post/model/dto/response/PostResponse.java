package com.example.dynamic_crud_rest_api.post.model.dto.response;

import com.example.dynamic_crud_rest_api.user.model.dto.response.UserResponse;

import java.time.LocalDateTime;

public record PostResponse(
        Long id,
        UserResponse createdBy,
        LocalDateTime createdAt,
        String title,
        String body
) {
    public static PostResponse of(Long id, UserResponse createdBy, LocalDateTime createdAt, String title, String body) {
        return new PostResponse(id, createdBy, createdAt, title, body);
    }
}
