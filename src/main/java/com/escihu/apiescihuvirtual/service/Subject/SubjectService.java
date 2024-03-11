package com.escihu.apiescihuvirtual.service.Subject;

import com.escihu.apiescihuvirtual.persistence.Entity.Subject.Subject;

import java.util.List;

public interface SubjectService {

    public Subject addSubject(Subject subject);

    public Subject updateSubject(Long id, Subject subject);

    public List<Subject> getAllSubjectsByCourseId(long courseId);

    public Subject getSubjectById(long id);

    public void deleteSubjectById(long id);

    public boolean existById(Long id);
}
