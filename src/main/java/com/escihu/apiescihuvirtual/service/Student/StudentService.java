package com.escihu.apiescihuvirtual.service.Student;

import com.escihu.apiescihuvirtual.Dto.Student.StudentDtoRequest;
import com.escihu.apiescihuvirtual.Dto.Student.StudentDtoResponse;
import com.escihu.apiescihuvirtual.Dto.Student.StudentUpdateDtoRequest;
import com.escihu.apiescihuvirtual.persistence.Entity.Student.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StudentService {
    public Student createStudent(StudentDtoRequest studentDtoRequest);

    public Student updateStudent(Long id, StudentUpdateDtoRequest studentDtoRequest);

    Page<StudentDtoResponse> listStudentsPaginated(Pageable pageable);

    public List<StudentDtoResponse> listStudents();

    public Student getStudentById(Long id);

    Student findByIdAndUsername(Long studentId, String username);

    public boolean exists(Long id);
}
