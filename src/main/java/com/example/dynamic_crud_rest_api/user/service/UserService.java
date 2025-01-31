package com.example.dynamic_crud_rest_api.user.service;

import com.example.dynamic_crud_rest_api.base.config.internationalization.Localization;
import com.example.dynamic_crud_rest_api.base.exception.ApiException;
import com.example.dynamic_crud_rest_api.base.model.response.ApiResponse;
import com.example.dynamic_crud_rest_api.base.service.BaseService;
import com.example.dynamic_crud_rest_api.user.model.dto.request.UserRequest;
import com.example.dynamic_crud_rest_api.user.model.dto.response.UserResponse;
import com.example.dynamic_crud_rest_api.user.model.entity.User;
import com.example.dynamic_crud_rest_api.user.model.filter_request.UserFilterRequest;
import com.example.dynamic_crud_rest_api.user.model.mapper.UserMapper;
import com.example.dynamic_crud_rest_api.user.repository.UserRepository;
import com.example.dynamic_crud_rest_api.user.specification.UserSpecification;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Getter
@Service
@RequiredArgsConstructor
public class UserService implements BaseService<UserRequest, User, Long, UserResponse, UserFilterRequest> {
    private final UserRepository repository;
    private final Localization localization;
    private final UserMapper mapper;
    private final UserSpecification specification;

    @PersistenceContext
    private final EntityManager entityManager;
    private final Class<User> classType=User.class;

    @Override
    public List<String> notUpdatableFields() {
        return List.of(User._deleted, User._id, User._createdAt, User._createdBy, User._updatedAt, User._updatedBy, User._permissions, User._role, User._password);
    }

    @Override
    public String getClassName() {
        return User.class.getSimpleName();
    }

    @Override
    public void checkUniqueWhileCreating(UserRequest userRequest) {
        if (repository.existsByPhone(userRequest.phone())) {
            throw new ApiException(localization.getMessage("user_already_exists_by_phone"));
        }
    }

    @Override
    public void checkUniqueWhileUpdating(UserRequest userRequest, Long id) {
        if (repository.existsByPhoneAndIdNot(userRequest.phone(), id)) {
            throw new ApiException(localization.getMessage("user_already_exists_by_phone"));
        }
    }

    @Override
    public ApiResponse<UserResponse> findById(Long id) {
        User user = entity(id);
        UserResponse userResponse = getMapper().toResponseWithPermissions(user);
        return ApiResponse.ok(userResponse);
    }
}
