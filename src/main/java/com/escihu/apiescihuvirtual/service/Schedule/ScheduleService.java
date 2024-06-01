package com.escihu.apiescihuvirtual.service.Schedule;

import com.escihu.apiescihuvirtual.persistence.Entity.Subject.SubjectSchedule;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public interface ScheduleService {
    /**
     * Guarda un horario de una materia.
     *
     * @param schedule el horario a guardar
     * @return el horario guardado {@link SubjectSchedule}
     */
    SubjectSchedule save(SubjectSchedule schedule);

    /**
     * Obtiene un horario por su id.
     *
     * @param scheduleId el id del horario a buscar
     * @return el horario {@link SubjectSchedule}
     */
    Optional<SubjectSchedule> getScheduleById(long scheduleId);

    /**
     * Elimina un horario.
     * @param scheduleId el id del horario a eliminar
     */
    void deleteSchedule(Long scheduleId);

    /**
     * Obtiene los horarios de un curso.
     *
     * @param courseId el id del curso
     * @return un mapa con los horarios de las materias del curso
     */
    HashMap<String, List<SubjectSchedule>> getScheduleByCourseId(long courseId);

    /**
     * Verifica si un horario existe por su id.
     * @param scheduleId el id del horario a verificar
     * @return true si existe, false si no
     */
    boolean existsSchedule(Long scheduleId);
}
