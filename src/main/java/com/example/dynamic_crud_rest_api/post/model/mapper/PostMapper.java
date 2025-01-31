package com.example.dynamic_crud_rest_api.post.model.mapper;

import com.example.dynamic_crud_rest_api.base.model.mapper.BaseMapper;
import com.example.dynamic_crud_rest_api.post.model.dto.request.PostRequest;
import com.example.dynamic_crud_rest_api.post.model.dto.response.PostResponse;
import com.example.dynamic_crud_rest_api.post.model.entity.Post;
import com.example.dynamic_crud_rest_api.user.model.dto.response.UserResponse;
import com.example.dynamic_crud_rest_api.user.model.entity.User;
import com.example.dynamic_crud_rest_api.user.model.mapper.UserMapper;
import com.example.dynamic_crud_rest_api.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostMapper implements BaseMapper<PostRequest, Post, PostResponse> {
    private final UserService userService;
    private final UserMapper userMapper;

    @Override
    public Post toEntity(PostRequest postRequest) {
        return Post.of(postRequest.title(), postRequest.body());
    }

    @Override
    public PostResponse toResponse(Post post) {
        User createdBy = userService.entity(post.getCreatedBy());
        UserResponse createdByResponse = userMapper.toResponse(createdBy);
        return PostResponse.of(post.getId(), createdByResponse, post.getCreatedAt(), post.getTitle(), post.getBody());
    }
}
