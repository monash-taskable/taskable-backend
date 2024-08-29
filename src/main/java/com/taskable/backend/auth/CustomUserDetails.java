package com.taskable.backend.auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public record CustomUserDetails(Integer userId, String sub) implements UserDetails {

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null; // Using 'sub' as the username for authentication purposes
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Set to true by default, or you can add logic to manage account expiration
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Set to true by default, or you can add logic to manage account locking
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Set to true by default, or you can add logic to manage credential expiration
    }

    @Override
    public boolean isEnabled() {
        return true; // Set to true by default, or you can add logic to manage account enabling/disabling
    }
}
