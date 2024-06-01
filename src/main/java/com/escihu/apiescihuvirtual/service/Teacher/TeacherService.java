package com.escihu.apiescihuvirtual.service.Teacher;

import com.escihu.apiescihuvirtual.Dto.Teacher.PaginatedTeacherDtoResponse;
import com.escihu.apiescihuvirtual.Dto.Teacher.TeacherDtoRequest;
import com.escihu.apiescihuvirtual.Dto.Teacher.TeacherDtoResponse;
import com.escihu.apiescihuvirtual.persistence.Entity.Teacher.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TeacherService {

    /**
     * Crea un nuevo profesor.
     *
     * @param teacherDtoRequest el objeto con los datos del profesor
     * @return el profesor creado {@link Teacher}
     */
    Teacher createTeacher(TeacherDtoRequest teacherDtoRequest);

    /**
     * Actualiza un profesor.
     *
     * @param teacherId el id del profesor a ser actualizado
     * @param teacherDtoRequest el object con los nuevos datos del profesor
     * @return el profesor actualizado {@link Teacher}
     */
    Teacher updateTeacher(Long teacherId, TeacherDtoRequest teacherDtoRequest);

    /**
     * Lista todos los profesores.
     *
     * @return una lista de objetos {@link Page} con todos los profesores {@link Teacher}
     */
    Page<Teacher> getAllTeachers(Pageable pageable);
    /**
     * Lista todos los profesores paginados.
     *
     * @param pageable el objeto pageable
     * @return un objeto Page con los profesores
     */
    PaginatedTeacherDtoResponse listTeachersPaginated(Pageable pageable);
    /**
     * Lista todos los profesores paginados.
     *
     * @param pageable el objeto pageable
     * @return un objeto Page con los profesores
     */
    Page<TeacherDtoResponse> teachersClassicPagination(Pageable pageable);

    /**
     * Lista todos los profesores.
     *
     * @return una lista de objetos {@link TeacherDtoResponse} con todos los profesores
     */
    List<TeacherDtoResponse> listTeachers();

    /**
     * Obtiene un profesor por su id.
     * @param teacherId el id del profesor a obtener
     * @ return el profesor {@link Teacher}
     */
    Teacher getTeacherById(Long teacherId);

    /**
     * Verifica si un profesor existe.
     *
     * @param teacherId el id del profesor
     * @return true si el profesor existe, false si no
     */
    boolean existsTeacher(Long teacherId);
}
