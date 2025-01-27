package com.example.joined_table_inheritance.config;

import com.example.joined_table_inheritance.config.utils.BaseUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@Configuration
@EnableJpaAuditing
public class JpaAuditingConfig {
    @Bean
    public AuditorAware<Long> auditorProvider() {
        return () -> Optional.of(BaseUtils.customUserDetails().getId());
    }
}
