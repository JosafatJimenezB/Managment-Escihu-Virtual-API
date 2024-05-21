package com.escihu.apiescihuvirtual.service.Teacher;

import com.escihu.apiescihuvirtual.Dto.Teacher.PaginatedTeacherDtoResponse;
import com.escihu.apiescihuvirtual.Dto.Teacher.TeacherDtoRequest;
import com.escihu.apiescihuvirtual.Dto.Teacher.TeacherDtoResponse;
import com.escihu.apiescihuvirtual.persistence.Entity.Teacher.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TeacherService {

    public Teacher createTeacher(TeacherDtoRequest teacherDtoRequest);

    public Teacher updateTeacher(Long id, TeacherDtoRequest teacherDtoRequest);

    public Page<Teacher> getAllTeachers(Pageable pageable);

    PaginatedTeacherDtoResponse listTeachersPaginated(Pageable pageable);

    public Page<TeacherDtoResponse> teachersClassicPagination(Pageable pageable);

    public List<TeacherDtoResponse> listTeachers();

    public Teacher getTeacherById(Long id);

    public boolean exists(Long id);
}
