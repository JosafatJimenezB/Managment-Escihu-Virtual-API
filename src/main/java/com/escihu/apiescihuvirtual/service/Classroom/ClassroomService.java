package com.escihu.apiescihuvirtual.service.Classroom;

import com.escihu.apiescihuvirtual.Dto.ClassroomDto;
import com.escihu.apiescihuvirtual.persistence.Entity.Classroom.Classroom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ClassroomService {
    /**
     * Agrega un aula
     * @param classroomDto objeto con los datos del aula {@link ClassroomDto}
     * @return el aula {@link Classroom}
     */
    Classroom addClassroom(ClassroomDto classroomDto);
    /**
     * Actualiza un aula
     * @param classroomId id del aula
     * @param classroomDto objeto con los datos del aula actualizados {@link ClassroomDto}
     * @return el aula {@link Classroom}
     */
    Classroom updateClassroom(Long classroomId, ClassroomDto classroomDto);

    /**
     * Obtiene las aulas paginadas
     * @param pageable objeto con los datos de paginación {@link Pageable}
     * @return lista de aulas {@link Classroom}
     */
    Page<Classroom> getAllClassrooms(Pageable pageable);
    /**
     * Obtiene todas las aulas
     * @return lista de aulas {@link Classroom}
     */
    List<Classroom> getAllClassrooms();

    /**
     * Obtiene el aula por id
     * @param classroomId id del aula
     * @return el aula {@link Classroom}
     */
    Classroom getClassroomById(long classroomId);
    /**
     * Comprueba si existe el aula
     * @param classroomId id del aula
     */
    boolean existsClassroom(Long classroomId);
}
