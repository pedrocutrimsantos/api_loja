package br.com.pauloneto.loja.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Optional;

public class SecurityAuditorAware implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof JwtAuthenticationToken jwt) {
            Object preferred = jwt.getToken().getClaims().get("preferred_username");
            if (preferred != null) return Optional.of(preferred.toString());
            return Optional.ofNullable(jwt.getName());
        }
        return Optional.ofNullable(auth != null ? auth.getName() : "sistema");
    }
}
