package com.daniel.delivery.config;

import com.daniel.delivery.DeliveryApplication;
import com.sun.xml.bind.v2.TODO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.envers.repository.config.EnableEnversRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;

import java.util.Optional;

@EnableJpaAuditing
@EnableEnversRepositories(basePackageClasses = DeliveryApplication.class)
@Configuration
public class AuditConfiguration {

    @Bean
    public AuditorAware<String> auditorAware() {
        return () -> Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .map(this::getPrincipal);
    }

    private String getPrincipal(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        if (principal instanceof DefaultOidcUser) {
            return (String) ((DefaultOidcUser) principal).getClaims().get("email");
        } else if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        }
        return null;
        //TODO: return exception

    }
}