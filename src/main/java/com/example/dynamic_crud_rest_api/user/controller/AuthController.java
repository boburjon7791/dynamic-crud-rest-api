package com.example.dynamic_crud_rest_api.user.controller;

import com.example.dynamic_crud_rest_api.base.model.response.ApiResponse;
import com.example.dynamic_crud_rest_api.base.utils.ApiConstants;
import com.example.dynamic_crud_rest_api.user.model.dto.request.LoginRequest;
import com.example.dynamic_crud_rest_api.user.model.dto.response.LoginResponse;
import com.example.dynamic_crud_rest_api.user.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiConstants.BASE_PATH+"/auth")
public class AuthController {
    private final AuthService service;

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
        return service.login(request);
    }
}
