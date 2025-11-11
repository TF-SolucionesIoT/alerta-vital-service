package com.iot.alertavital.iam.infrastructure.security;

public class CustomUserPrincipal {
    private Long id;
    private String roleOrType;

    public CustomUserPrincipal(Long id, String roleOrType) {
        this.id = id;
        this.roleOrType = roleOrType;
    }

    public Long getId() {
        return id;
    }

    public String getRoleOrType() {
        return roleOrType;
    }
}