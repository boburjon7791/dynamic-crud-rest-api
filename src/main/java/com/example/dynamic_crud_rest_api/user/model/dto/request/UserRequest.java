package com.example.dynamic_crud_rest_api.user.model.dto.request;

import com.example.dynamic_crud_rest_api.user.model.enums.Permission;
import com.example.dynamic_crud_rest_api.user.model.enums.Role;
import jakarta.validation.constraints.NotBlank;

import java.util.HashSet;
import java.util.Set;

public record UserRequest(
        @NotBlank
        String firstName,

        @NotBlank
        String lastName,

        @NotBlank
        String fatherName,

        @NotBlank
        String phone,

        Role role,

        Set<Permission> permissions,

        String password
) {
    public UserRequest{
        permissions=permissions==null ? new HashSet<>() : permissions;
    }
}
