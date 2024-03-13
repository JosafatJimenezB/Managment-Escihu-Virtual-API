package com.escihu.apiescihuvirtual.Dto;

import com.escihu.apiescihuvirtual.persistence.Entity.Role;

public record LoginResponse(Long id,String username, String token, Role role) {
}
