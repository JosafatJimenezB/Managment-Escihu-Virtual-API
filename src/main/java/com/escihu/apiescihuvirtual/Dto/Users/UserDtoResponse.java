package com.escihu.apiescihuvirtual.Dto.Users;

import com.escihu.apiescihuvirtual.persistence.Entity.Role;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Builder
public class UserDtoResponse {

    Long id;
    String username;
    String email;
    Long userAsigned;
    Role role;

    public UserDtoResponse(Long id, String username, String email, Long userAsigned, Role role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.userAsigned = userAsigned;
        this.role = role;
    }

    public UserDtoResponse(Long userId, String username, String email, @NotNull() Long userAsigned, Collection<? extends GrantedAuthority> authorities) {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getUserAsigned() {
        return userAsigned;
    }

    public void setUserAsigned(Long userAsigned) {
        this.userAsigned = userAsigned;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
