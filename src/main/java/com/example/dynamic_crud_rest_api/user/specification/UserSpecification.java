package com.example.dynamic_crud_rest_api.user.specification;

import com.example.dynamic_crud_rest_api.base.specification.BaseSpecification;
import com.example.dynamic_crud_rest_api.user.model.entity.User;
import com.example.dynamic_crud_rest_api.user.model.filter_request.UserFilterRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class UserSpecification implements BaseSpecification<User, Long, UserFilterRequest> {
    @Override
    public Specification<User> getSpecification(UserFilterRequest request) {
        return null;
    }
}
