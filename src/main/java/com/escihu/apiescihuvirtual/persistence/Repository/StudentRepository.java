package com.escihu.apiescihuvirtual.persistence.Repository;

import com.escihu.apiescihuvirtual.persistence.Entity.Student.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Student findByUserUserId(Long studentId);

    Student findByIdAndUserUsername(Long studentId, String username);

    Student findByCorreoPersonal(String correoPersonal);

}
