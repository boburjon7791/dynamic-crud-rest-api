package com.example.dynamic_crud_rest_api.base.config;

import com.example.dynamic_crud_rest_api.base.utils.BaseUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@Configuration
@EnableJpaAuditing
public class JpaAuditingConfig {
    @Bean
    public AuditorAware<Long> auditorAware() {
        return () -> Optional.of(BaseUtils.currentAuthenticatedUser().getId());
    }
}
