package com.example.dynamic_crud_rest_api.post.service;

import com.example.dynamic_crud_rest_api.base.config.internationalization.Localization;
import com.example.dynamic_crud_rest_api.base.service.BaseService;
import com.example.dynamic_crud_rest_api.post.model.dto.request.PostRequest;
import com.example.dynamic_crud_rest_api.post.model.dto.response.PostResponse;
import com.example.dynamic_crud_rest_api.post.model.entity.Post;
import com.example.dynamic_crud_rest_api.post.model.filter_request.PostFilterRequest;
import com.example.dynamic_crud_rest_api.post.model.mapper.PostMapper;
import com.example.dynamic_crud_rest_api.post.repository.PostRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Getter
@Service
@RequiredArgsConstructor
public class PostService implements BaseService<PostRequest, Post, Long, PostResponse, PostFilterRequest> {
    private final PostRepository repository;
    private final PostMapper mapper;
    private final com.example.dynamic_crud_rest_api.post.specification.PostSpecification specification;
    private final Localization localization;

    @PersistenceContext
    private final EntityManager entityManager;
    private final Class<Post> classType= Post.class;

    @Override
    public String getClassName() {
        return Post.class.getSimpleName();
    }

    @Override
    public void checkUniqueWhileCreating(PostRequest postRequest) {

    }

    @Override
    public void checkUniqueWhileUpdating(PostRequest postRequest, Long id) {

    }

    @Override
    public PostRequest cleanUpdateModelObject(PostRequest postRequest) {
        return postRequest;
    }
}
