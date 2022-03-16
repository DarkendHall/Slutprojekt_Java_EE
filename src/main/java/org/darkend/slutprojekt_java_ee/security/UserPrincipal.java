package org.darkend.slutprojekt_java_ee.security;

import org.darkend.slutprojekt_java_ee.entity.RoleEntity;
import org.darkend.slutprojekt_java_ee.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class UserPrincipal implements UserDetails {

    private final String username;
    private final String password;
    private final Set<String> roles;

    public UserPrincipal(UserEntity user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.roles = user.getRoles()
                .stream()
                .map(RoleEntity::getRole)
                .map(String::toUpperCase)
                .collect(Collectors.toSet());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
