package com.example.dynamic_crud_rest_api;

import com.example.dynamic_crud_rest_api.base.config.security.CustomUserDetails;
import com.example.dynamic_crud_rest_api.base.config.security.CustomUserDetailsService;
import com.example.dynamic_crud_rest_api.base.specification.UpdateSpecification;
import com.example.dynamic_crud_rest_api.post.model.dto.request.PostRequest;
import com.example.dynamic_crud_rest_api.post.model.entity.Post;
import com.example.dynamic_crud_rest_api.post.repository.PostRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

@Configuration
@RequiredArgsConstructor
public class CustomUpdateSpecificationsTest {
    private final EntityManager entityManager;
    private final PostRepository postRepository;
    private final CustomUserDetailsService customUserDetailsService;

    @Transactional
    public void test() {
        CustomUserDetails customUserDetails = customUserDetailsService.loadUserByUsername("8123");
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities()));

        PostRequest postRequest = new PostRequest("test", "aaaaa");
        UpdateSpecification<Post> updateSpecification = (criteriaBuilder, criteriaUpdate, root) -> {
            criteriaUpdate.set("title", postRequest.title());
            return criteriaBuilder.equal(root.get("title"), "title");
        };
//        postRepository.executeUpdate(updateSpecification, Post.class, entityManager);

        SecurityContextHolder.clearContext();
    }
}
