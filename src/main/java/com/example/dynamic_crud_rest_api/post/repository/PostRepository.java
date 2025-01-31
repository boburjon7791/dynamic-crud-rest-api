package com.example.dynamic_crud_rest_api.post.repository;

import com.example.dynamic_crud_rest_api.base.repository.BaseRepository;
import com.example.dynamic_crud_rest_api.post.model.entity.Post;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends BaseRepository<Post, Long> {
}
