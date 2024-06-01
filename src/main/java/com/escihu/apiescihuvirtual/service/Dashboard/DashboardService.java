package com.escihu.apiescihuvirtual.service.Dashboard;

import com.escihu.apiescihuvirtual.persistence.Entity.Course.Course;
import com.escihu.apiescihuvirtual.persistence.Entity.Subject.Subject;

import java.util.List;

public interface DashboardService {

    /**
     * Obtiene el total de estudiantes.
     * @return el total de estudiantes
     */
    int getTotalStudents();

    /**
     * Obtiene el total de profesores.
     * @return el total de profesores
     */
    int getTotalTeachers();

    /**
     * Obtiene el total de aulas.
     * @return el total de aulas
     */
    int getTotalClassrooms();

    /**
     * Obtiene los cursos de un profesor.
     * @param username el nombre de usuario del profesor
     * @return la lista de cursos
     */
    List<Course> getCoursesByTeacher(String username);

    /**
     * Obtiene las materias de un profesor.
     * @param username el nombre de usuario del profesor
     * @return la lista de materias
     */
    List<Subject> getSubjectsByTeacher(String username);
}
