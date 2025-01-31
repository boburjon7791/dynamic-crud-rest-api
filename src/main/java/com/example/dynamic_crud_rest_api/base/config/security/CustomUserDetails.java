package com.example.dynamic_crud_rest_api.base.config.security;

import com.example.dynamic_crud_rest_api.user.model.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public record CustomUserDetails(User user) implements UserDetails {
    public static CustomUserDetails of(User user){
        return new CustomUserDetails(user);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> roles = Stream.of(user.getRole()).map(role -> new SimpleGrantedAuthority("ROLE_" + role)).collect(Collectors.toSet());
        Set<SimpleGrantedAuthority> permissions = user.getPermissions().stream().map(permission -> new SimpleGrantedAuthority(permission.name())).collect(Collectors.toSet());
        roles.addAll(permissions);
        return roles;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getPhone();
    }

    @Override
    public boolean isEnabled() {
        return !user.isDeleted();
    }

    public Long getId(){
        return user.getId();
    }
}
