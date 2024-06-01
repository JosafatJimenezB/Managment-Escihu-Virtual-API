package com.escihu.apiescihuvirtual.service.Course;

import com.escihu.apiescihuvirtual.Dto.CourseDtoRequest;
import com.escihu.apiescihuvirtual.persistence.Entity.Course.Course;
import com.escihu.apiescihuvirtual.persistence.Entity.Cycle.Cycle;

import java.util.List;

public interface CourseService {

    /**
     * Agrega un curso.
     * @param courseDtoRequest objeto con los datos del curso {@link CourseDtoRequest}
     */
    void addCourse(CourseDtoRequest courseDtoRequest);
    /**
     * Actualiza un curso.
     * @param courseId el id del curso
     * @param courseDtoRequest objeto con los datos del curso actualizados  {@link CourseDtoRequest}
     * @return el curso {@link Course}
     */
    Course updateCourse(Long courseId, CourseDtoRequest courseDtoRequest);

    /**
     * Obtiene todos los cursos de un ciclo.
     * @param idCycle id del ciclo
     * @return lista de cursos {@link Course}
     */
    List<Course> getAllCoursesByCycleId(long idCycle);

    /**
     * Obtiene todos los cursos
     * @return lista de cursos {@link Course}
     */
    List<Course> getAllCoursesByCycle(Cycle cycle);
    /**
     * Obtiene el curso por id
     * @param courseId  del curso
     * @return lista de cursos {@link Course}
     */
    Course getCourseById(long courseId);

    /**
     * Saca un estudiante de un curso
     * @param courseId id del curso
     * @param studentId id del estudiante
     */
    void unsubscribeStudent(long courseId, long studentId);

    /**
     * Elimina un curso
     * @param courseId id del curso
     */
    void deleteCourseById(long courseId);

    void deleteCourse(Course course);

    boolean existCourse(Long id);
}
