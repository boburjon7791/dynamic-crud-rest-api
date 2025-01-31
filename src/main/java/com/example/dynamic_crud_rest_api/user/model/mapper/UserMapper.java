package com.example.dynamic_crud_rest_api.user.model.mapper;

import com.example.dynamic_crud_rest_api.base.model.mapper.BaseMapper;
import com.example.dynamic_crud_rest_api.user.model.dto.request.UserRequest;
import com.example.dynamic_crud_rest_api.user.model.dto.response.UserResponse;
import com.example.dynamic_crud_rest_api.user.model.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class UserMapper implements BaseMapper<UserRequest, User, UserResponse> {
    private final PasswordEncoder passwordEncoder;

    @Override
    public User toEntity(UserRequest userRequest) {
        String password = passwordEncoder.encode(Objects.requireNonNull(userRequest.password(), "password is null"));
        return User.of(userRequest.firstName(), userRequest.lastName(), userRequest.fatherName(), userRequest.phone(), password, userRequest.role(), userRequest.permissions());
    }

    @Override
    public UserResponse toResponse(User user) {
        return UserResponse.of(user.getId(), user.getFirstName(), user.getLastName(), user.getFatherName(), user.getPhone(), user.getRole(), new HashSet<>());
    }

    public UserResponse toResponseWithPermissions(User user) {
        return UserResponse.of(user.getId(), user.getFirstName(), user.getLastName(), user.getFatherName(), user.getPhone(), user.getRole(), user.getPermissions());
    }
}
