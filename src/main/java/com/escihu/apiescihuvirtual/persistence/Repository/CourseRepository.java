package com.escihu.apiescihuvirtual.persistence.Repository;

import com.escihu.apiescihuvirtual.persistence.Entity.Course.Course;
import com.escihu.apiescihuvirtual.persistence.Entity.Cycle.Cycle;
import com.escihu.apiescihuvirtual.persistence.Entity.Teacher.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    public List<Course> findByCycle(Cycle cycle);

    public List<Course> findByManager(Teacher manager);
}
