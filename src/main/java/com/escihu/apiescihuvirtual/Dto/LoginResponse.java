package com.escihu.apiescihuvirtual.Dto;

import com.escihu.apiescihuvirtual.persistence.Entity.Role;
import com.escihu.apiescihuvirtual.persistence.Entity.Student.Student;
import com.escihu.apiescihuvirtual.persistence.Entity.Teacher.Teacher;

public record LoginResponse(Long id, String username, String token, Student student, Teacher teacher, Role role) {
}
