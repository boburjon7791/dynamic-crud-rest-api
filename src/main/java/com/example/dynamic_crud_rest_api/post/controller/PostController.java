package com.example.dynamic_crud_rest_api.post.controller;

import com.example.dynamic_crud_rest_api.base.controller.BaseController;
import com.example.dynamic_crud_rest_api.base.utils.ApiConstants;
import com.example.dynamic_crud_rest_api.post.model.dto.request.PostRequest;
import com.example.dynamic_crud_rest_api.post.model.dto.response.PostResponse;
import com.example.dynamic_crud_rest_api.post.model.entity.Post;
import com.example.dynamic_crud_rest_api.post.model.filter_request.PostFilterRequest;
import com.example.dynamic_crud_rest_api.post.service.PostService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Getter
@RestController
@RequiredArgsConstructor
@RequestMapping(ApiConstants.BASE_PATH+"/posts")
public class PostController implements BaseController<PostRequest, Post, Long, PostResponse, PostFilterRequest> {
    private final PostService service;
}
