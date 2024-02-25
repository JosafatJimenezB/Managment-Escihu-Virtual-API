package com.escihu.apiescihuvirtual.service.Subject;

import com.escihu.apiescihuvirtual.persistence.Entity.Course.Course;
import com.escihu.apiescihuvirtual.persistence.Entity.Subject.Subject;
import com.escihu.apiescihuvirtual.persistence.Repository.CourseRepository;
import com.escihu.apiescihuvirtual.persistence.Repository.SubjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectServiceImpl implements SubjectService{

    private final SubjectRepository subjectRepository;
    private final CourseRepository courseRepository;

    public SubjectServiceImpl(SubjectRepository subjectRepository, CourseRepository courseRepository) {
        this.subjectRepository = subjectRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public void addSubject(Subject subject) {
        subjectRepository.save(subject);
    }

    @Override
    public void updateSubject(Subject sform) {
        Subject subject = subjectRepository.getOne(sform.getId());

        subject.setName(sform.getName());
        subject.setClassroom(sform.getClassroom());
        subject.setTeacher(sform.getTeacher());

        subjectRepository.save(subject);
    }

    @Override
    public List<Subject> getAllSubjectsByCourseId(long courseId) {
        Course course = courseRepository.findById(courseId).orElse(null);

        return subjectRepository.findByCourse(course);
    }

    @Override
    public Subject getSubjectById(long id) {
        return subjectRepository.getOne(id);
    }

    @Override
    public void deleteSubjectById(long id) {
        Subject subject = subjectRepository.getOne(id);

        subjectRepository.delete(subject);
    }
}
