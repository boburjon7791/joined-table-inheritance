package com.example.joined_table_inheritance.config.security;

import com.example.joined_table_inheritance.model.entity.User;
import com.example.joined_table_inheritance.model.enums.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

public record CustomUserDetails(User user, Collection<Role> roles) implements UserDetails {
    public static CustomUserDetails of(User user, Set<Role> roles) {
        return new CustomUserDetails(user, roles);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(role -> new SimpleGrantedAuthority("ROLE_"+role.name())).toList();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    public Long getId(){
        return user.getId();
    }
}
