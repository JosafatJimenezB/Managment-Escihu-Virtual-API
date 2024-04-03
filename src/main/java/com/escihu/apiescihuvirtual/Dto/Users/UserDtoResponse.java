package com.escihu.apiescihuvirtual.Dto.Users;

import com.escihu.apiescihuvirtual.persistence.Entity.Role;
import lombok.Builder;

@Builder
public class UsersDto {

    Long id;
    String username;
    String email;
    Long userAsigned;
    Role role;

    public UsersDto(Long id, String username, String email, Long userAsigned, Role role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.userAsigned = userAsigned;
        this.role = role;
    }

    public UsersDto() {

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
