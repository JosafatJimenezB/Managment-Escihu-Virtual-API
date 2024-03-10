package com.escihu.apiescihuvirtual.service.Course;

import com.escihu.apiescihuvirtual.Dto.CourseDtoRequest;
import com.escihu.apiescihuvirtual.persistence.Entity.Course.Course;
import com.escihu.apiescihuvirtual.persistence.Entity.Cycle.Cycle;
import com.escihu.apiescihuvirtual.persistence.Entity.Student.Student;
import com.escihu.apiescihuvirtual.persistence.Repository.CourseRepository;
import com.escihu.apiescihuvirtual.persistence.Repository.CycleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService{

    private final CourseRepository courseRepository;
    private final CycleRepository cycleRepository;

    public CourseServiceImpl(CourseRepository courseRepository, CycleRepository cycleRepository) {
        this.courseRepository = courseRepository;
        this.cycleRepository = cycleRepository;
    }

    @Override
    public void addCourse(CourseDtoRequest courseDtoRequest) {

        Course course = Course.builder()
                .name(courseDtoRequest.getName())
                .manager(courseDtoRequest.getManager())
                .classroom(courseDtoRequest.getClassroom())
                .build();

        courseRepository.save(course);
    }

    @Override
    public Course updateCourse(Long id, CourseDtoRequest courseDtoRequest) {
        Optional<Course> courseExists = courseRepository.findById(id);

        if(!courseExists.isPresent()){
            return null;
        }

        Course course = courseExists.get();

        course = Course.builder()
                .name(courseDtoRequest.getName())
                .manager(courseDtoRequest.getManager())
                .classroom(courseDtoRequest.getClassroom())
                .build();

        return courseRepository.save(course);
    }

    @Override
    public List<Course> getAllCoursesByCycleId(long idCycle) {
        Optional<Cycle> cycleOptional = cycleRepository.findById(idCycle);
        if (!cycleOptional.isPresent()) {
            return null;
        }

        return courseRepository.findByCycle(cycleOptional.get());
    }

    @Override
    public List<Course> getAllCoursesByCycle(Cycle cycle) {
        return courseRepository.findByCycle(cycle);
    }

    @Override
    public Course getCourseById(long id) {
        return courseRepository.findById(id).orElse(null);
    }

    @Override
    public void unsubscribeStudent(long courseId, long studentId) {
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        if (courseOptional.isPresent()) {
            Course course = courseOptional.get();
            course.getStudents().removeIf(student -> student.getId() == studentId);
            courseRepository.save(course);
        }
    }

    @Override
    public void deleteCourseById(long courseId) {
        courseRepository.deleteById(courseId);
    }

    @Override
    public void deleteCourse(Course course) {
        courseRepository.delete(course);
    }

    @Override
    public boolean existById(Long id) {
        return courseRepository.existsById(id);
    }
}
