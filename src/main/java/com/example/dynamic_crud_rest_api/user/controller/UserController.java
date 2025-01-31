package com.example.dynamic_crud_rest_api.user.controller;

import com.example.dynamic_crud_rest_api.base.controller.BaseController;
import com.example.dynamic_crud_rest_api.base.utils.ApiConstants;
import com.example.dynamic_crud_rest_api.user.model.dto.request.UserRequest;
import com.example.dynamic_crud_rest_api.user.model.dto.response.UserResponse;
import com.example.dynamic_crud_rest_api.user.model.entity.User;
import com.example.dynamic_crud_rest_api.user.model.filter_request.UserFilterRequest;
import com.example.dynamic_crud_rest_api.user.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Getter
@RestController
@RequiredArgsConstructor
@RequestMapping(ApiConstants.BASE_PATH+"/users")
public class UserController implements BaseController<UserRequest, User, Long, UserResponse, UserFilterRequest> {
    private final UserService service;
}
