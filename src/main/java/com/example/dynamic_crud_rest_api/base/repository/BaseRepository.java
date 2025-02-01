package com.example.dynamic_crud_rest_api.base.repository;

import com.example.dynamic_crud_rest_api.base.model.entity.BaseSuperEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository<T extends BaseSuperEntity, ID> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T>, UpdateSpecificationExecutor<T>, DeleteSpecificationExecutor<T> {
}
