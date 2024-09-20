package com.escihu.apiescihuvirtual.persistence.Repository;

import com.escihu.apiescihuvirtual.persistence.Entity.Enums.StatusStudent;
import com.escihu.apiescihuvirtual.persistence.Entity.Student.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Student findByUserUserId(Long studentId);

    Optional<Student> findById(Long studentId);

    Student findByCorreoPersonal(String correoPersonal);

    List<Student> findAllByStatusAlumno(StatusStudent statusAlumno);

}
