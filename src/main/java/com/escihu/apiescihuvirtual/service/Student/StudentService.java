package com.escihu.apiescihuvirtual.service.Student;

import com.escihu.apiescihuvirtual.Dto.Student.*;
import com.escihu.apiescihuvirtual.persistence.Entity.Student.Student;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StudentService {
    public Student createStudent(StudentDtoRequest studentDtoRequest);

    public Student updateStudent(Long id, StudentUpdateDtoRequest studentDtoRequest);

    PaginatedStudentDtoResponse listStudentsPaginated(Pageable pageable);

    public List<StudentDtoResponse> listStudents();

    public Student getStudentById(Long id);

    public boolean exists(Long id);
}
