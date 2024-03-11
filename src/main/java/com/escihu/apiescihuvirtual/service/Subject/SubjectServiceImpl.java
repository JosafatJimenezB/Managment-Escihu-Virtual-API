package com.escihu.apiescihuvirtual.service.Subject;

import com.escihu.apiescihuvirtual.persistence.Entity.Course.Course;
import com.escihu.apiescihuvirtual.persistence.Entity.Subject.Subject;
import com.escihu.apiescihuvirtual.persistence.Repository.CourseRepository;
import com.escihu.apiescihuvirtual.persistence.Repository.SubjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubjectServiceImpl implements SubjectService{

    private final SubjectRepository subjectRepository;
    private final CourseRepository courseRepository;

    public SubjectServiceImpl(SubjectRepository subjectRepository, CourseRepository courseRepository) {
        this.subjectRepository = subjectRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public Subject addSubject(Subject subject) {
        return subjectRepository.save(subject);
    }

    @Override
    public Subject updateSubject(Long id, Subject sform) {
        Optional<Subject> subjectExist = subjectRepository.findById(id);

        if(!subjectExist.isPresent()) {
            return null;
        }

        Subject subject = subjectExist.get();

        subject.setName(sform.getName());
        subject.setClassroom(sform.getClassroom());
        subject.setTeacher(sform.getTeacher());

        return subjectRepository.save(subject);
    }

    @Override
    public List<Subject> getAllSubjectsByCourseId(long courseId) {
        Course course = courseRepository.findById(courseId).orElse(null);

        return subjectRepository.findByCourse(course);
    }

    @Override
    public Subject getSubjectById(long id) {
        return subjectRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteSubjectById(long id) {
        Subject subject = subjectRepository.findById(id).orElse(null);

        subjectRepository.delete(subject);
    }

    @Override
    public boolean existById(Long id) {
        return subjectRepository.existsById(id);
    }
}
