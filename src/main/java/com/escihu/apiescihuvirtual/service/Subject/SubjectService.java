package com.escihu.apiescihuvirtual.service.Subject;

import com.escihu.apiescihuvirtual.persistence.Entity.Subject.Subject;

import java.util.List;

public interface SubjectService {

    Subject addSubject(Subject subject);

    Subject updateSubject(Long id, Subject subject);

    List<Subject> getAllSubjectsByCourseId(long courseId);

    Subject getSubjectById(long id);

    void deleteSubjectById(long id);

    boolean existById(Long id);
}
