package com.escihu.apiescihuvirtual.service.Grade;

import com.escihu.apiescihuvirtual.Dto.Grade.GradeDtoRequest;
import com.escihu.apiescihuvirtual.persistence.Entity.Grade.Grade;
import com.escihu.apiescihuvirtual.persistence.Entity.Grade.GradeDetail;
import com.escihu.apiescihuvirtual.persistence.Entity.Subject.Subject;

import java.util.List;

public interface GradeService {

    public Grade addGrade(GradeDtoRequest gradeDtoRequest);

    public Grade getGradeById(long id);

    public List<Grade> getGradesBySubject(Subject subject);

    public List<Grade> getGradesBySubjectId(long subjectId);

    public Grade updateGrade(Long id, GradeDtoRequest gradeDtoRequest);

    public void deleteGradeById(long id);

    public void deleteGrade(Grade grade);

    public List<GradeDetail> getGradeScorebook(long gradeId);

    public boolean existById(Long id);
}
