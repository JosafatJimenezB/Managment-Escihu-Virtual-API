package com.escihu.apiescihuvirtual.service.Grade;

import com.escihu.apiescihuvirtual.Dto.Grade.GradeDtoRequest;
import com.escihu.apiescihuvirtual.persistence.Entity.Grade.Grade;
import com.escihu.apiescihuvirtual.persistence.Entity.Grade.GradeDetail;
import com.escihu.apiescihuvirtual.persistence.Entity.Subject.Subject;

import java.util.List;

public interface GradeService {

    /**
     * Agrega una calificación.
     * @param gradeDtoRequest objeto con los datos de la calificación {@link GradeDtoRequest}
     * @return Lista de calificaciones {@link Grade}
     */
    Grade addGrade(GradeDtoRequest gradeDtoRequest);

    /**
     * Obtiene una calificación por su id.
     * @param gradeId el id de la calificación
     * @return la calificación {@link Grade}
     */
    Grade getGradeById(long gradeId);


    List<Grade> getGradesBySubject(Subject subject);

    List<Grade> getGradesBySubjectId(long subjectId);

    Grade updateGrade(Long id, GradeDtoRequest gradeDtoRequest);
    /**
     * Elimina una calificación por su id.
     * @param id el id de la calificación
     */
    void deleteGradeById(long id);

    void deleteGrade(Grade grade);

    List<GradeDetail> getGradeScorebook(long gradeId);

    boolean existById(Long id);
}
