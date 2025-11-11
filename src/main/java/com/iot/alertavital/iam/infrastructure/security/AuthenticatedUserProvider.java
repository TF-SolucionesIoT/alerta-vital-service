package com.iot.alertavital.iam.infrastructure.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticatedUserProvider {
    public Long getCurrentUserId() {
        CustomUserPrincipal principal = getPrincipal();
        return principal.getId();
    }

    public String getCurrentUserType() {
        CustomUserPrincipal principal = getPrincipal();
        return principal.getRoleOrType();
    }

    private CustomUserPrincipal getPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("No JWT authentication found");
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof CustomUserPrincipal customPrincipal) {
            return customPrincipal;
        }

        throw new IllegalStateException("Invalid authentication principal type: " + principal.getClass().getName());
    }
}