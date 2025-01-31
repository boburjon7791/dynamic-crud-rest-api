package com.example.dynamic_crud_rest_api.post.specification;

import com.example.dynamic_crud_rest_api.base.specification.BaseSpecification;
import com.example.dynamic_crud_rest_api.post.model.entity.Post;
import com.example.dynamic_crud_rest_api.post.model.filter_request.PostFilterRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class PostSpecification implements BaseSpecification<Post, Long, PostFilterRequest> {
    @Override
    public Specification<Post> getSpecification(PostFilterRequest request) {
        return null;
    }
}
