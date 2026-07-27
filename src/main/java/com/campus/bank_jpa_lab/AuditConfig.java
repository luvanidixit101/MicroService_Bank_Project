package com.campus.bank_jpa_lab;

import java.util.Optional;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing                       // wire 1: switch the feature ON
public class AuditConfig {

    @Bean
    public AuditorAware<String> auditorProvider() {
        // wire 3: who is the "current user"?
        // (real banks read this from Spring Security's logged-in user)
        return () -> Optional.of("branch-manager");
    }
}