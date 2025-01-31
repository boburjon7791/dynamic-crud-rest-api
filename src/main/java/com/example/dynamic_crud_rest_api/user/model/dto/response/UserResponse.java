package com.example.dynamic_crud_rest_api.user.model.dto.response;

import com.example.dynamic_crud_rest_api.user.model.enums.Permission;
import com.example.dynamic_crud_rest_api.user.model.enums.Role;

import java.util.Set;

public record UserResponse(
        Long id,
        String firstName,
        String lastName,
        String fatherName,
        String phone,
        Role role,
        Set<Permission> permissions
) {
    public static UserResponse of(Long id, String firstName, String lastName, String fatherName, String phone, Role role, Set<Permission> permissions) {
        return new UserResponse(id, firstName, lastName, fatherName, phone, role, permissions);
    }
}
