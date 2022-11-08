package com.daniel.delivery.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER,
    ADMIN,

    COURIER;

    @Override
    public String getAuthority() {
        return name();
    }
}