package com.example.dynamic_crud_rest_api.user.repository;

import com.example.dynamic_crud_rest_api.base.repository.BaseRepository;
import com.example.dynamic_crud_rest_api.user.model.entity.User;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends BaseRepository<User, Long> {
    @EntityGraph(attributePaths = {User._permissions})
    Optional<User> findByPhoneAndDeletedFalse(String phone);

    @Override
    @EntityGraph(attributePaths = {User._permissions})
    Optional<User> findOne(Specification<User> spec);

    boolean existsByPhone(String phone);
    boolean existsByPhoneAndIdNot(String phone, Long id);
}
