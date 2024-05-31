package com.escihu.apiescihuvirtual.service.Dashboard;

import com.escihu.apiescihuvirtual.persistence.Entity.Course.Course;
import com.escihu.apiescihuvirtual.persistence.Entity.Subject.Subject;
import com.escihu.apiescihuvirtual.persistence.Entity.Teacher.Teacher;
import com.escihu.apiescihuvirtual.persistence.Repository.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final ClassroomRepository classroomRepository;
    private final CourseRepository courseRepository;
    private final TeacherRepository teacherRepository;
    private final SubjectRepository subjectRepository;
    private final StudentRepository studentRepository;

    public DashboardServiceImpl(ClassroomRepository classroomRepository, CourseRepository courseRepository, TeacherRepository teacherRepository, SubjectRepository subjectRepository, StudentRepository studentRepository) {
        this.classroomRepository = classroomRepository;
        this.courseRepository = courseRepository;
        this.teacherRepository = teacherRepository;
        this.subjectRepository = subjectRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public int getTotalStudents() {
        return (int) studentRepository.count();
    }

    @Override
    public int getTotalTeachers() {
        return (int) teacherRepository.count();
    }

    @Override
    public int getTotalClassrooms() {
        return (int) classroomRepository.count();
    }

    @Override
    public List<Course> getCoursesByTeacher(String nombre) {
        Teacher manager = teacherRepository.findUSerByNombre(nombre);
        return courseRepository.findByManager(manager);
    }

    @Override
    public List<Subject> getSubjectsByTeacher(String nombre) {
        Teacher teacher = teacherRepository.findUSerByNombre(nombre);
        return subjectRepository.findByTeacher(teacher);
    }
}
