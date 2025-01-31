package com.example.dynamic_crud_rest_api.user.service;

import com.example.dynamic_crud_rest_api.base.config.security.CustomUserDetails;
import com.example.dynamic_crud_rest_api.base.config.security.JwtProvider;
import com.example.dynamic_crud_rest_api.base.config.security.TokenDTO;
import com.example.dynamic_crud_rest_api.base.exception.ApiException;
import com.example.dynamic_crud_rest_api.base.model.response.ApiResponse;
import com.example.dynamic_crud_rest_api.base.model.response.ResultCodes;
import com.example.dynamic_crud_rest_api.user.model.dto.request.LoginRequest;
import com.example.dynamic_crud_rest_api.user.model.dto.response.LoginResponse;
import com.example.dynamic_crud_rest_api.user.model.dto.response.UserResponse;
import com.example.dynamic_crud_rest_api.user.model.mapper.UserMapper;
import com.example.dynamic_crud_rest_api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final UserMapper userMapper;

    public ApiResponse<LoginResponse> login(LoginRequest request) {
        CustomUserDetails customUserDetails;
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.login(), request.password()));
            customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        }catch (AuthenticationException e){
            throw new ApiException(e.getMessage());
        }
        TokenDTO tokenDTO = jwtProvider.generateTokens(customUserDetails.getUsername(), customUserDetails.getId());
        UserResponse userResponse = userMapper.toResponse(customUserDetails.user());
        LoginResponse response = LoginResponse.of(userResponse, tokenDTO);
        return ApiResponse.ok(response);
    }
}
