package com.escihu.apiescihuvirtual.service.Course;

import com.escihu.apiescihuvirtual.persistence.Entity.Course.Course;
import com.escihu.apiescihuvirtual.persistence.Entity.Cycle.Cycle;

import java.util.List;

public interface CourseService {

    public void addCourse(Course course);

    public void updateCourse(Course course);

    public List<Course> getAllCoursesByCycleId(long idCycle);

    public List<Course> getAllCoursesByCycle(Cycle cycle);

    public Course getCourseById(long id);

    public void unsubscribeStudent(long courseId, long studentId);

    public void deleteCourseById(long courseId);

    public void deleteCourse(Course course);
}
