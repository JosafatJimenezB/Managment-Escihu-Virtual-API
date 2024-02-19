package com.escihu.apiescihuvirtual.persistence.Repository;

import com.escihu.apiescihuvirtual.persistence.Entity.Course.Course;
import com.escihu.apiescihuvirtual.persistence.Entity.Subject.Subject;
import com.escihu.apiescihuvirtual.persistence.Entity.Teacher.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {

    public List<Subject> findByCourse(Course course);

    public List<Subject> findByTeacher(Teacher teacher);
}
