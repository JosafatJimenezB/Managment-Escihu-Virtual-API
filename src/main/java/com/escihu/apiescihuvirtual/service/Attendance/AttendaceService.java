package com.escihu.apiescihuvirtual.service.Attendance;

import com.escihu.apiescihuvirtual.Dto.Attendance.AttendaceDtoRequest;
import com.escihu.apiescihuvirtual.persistence.Entity.Attendance.Attendance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AttendaceService {
    /**
     * Registra la asistencia de un usuario
     * @param attendaceDtoRequest objeto con los datos de la asistencia {@link AttendaceDtoRequest}
     * @return la asistencia {@link Attendance}
     */
    Attendance register(AttendaceDtoRequest attendaceDtoRequest);
    /**
     * Obtiene la asistencia paginada
     * @param pageable objeto con los datos de paginación {@link Pageable}
     * @return lista de asistencias {@link Attendance}
     */
    Page<Attendance> attendancePagination(Pageable pageable);
    /**
     * Obtiene todas las asistencias
     * @return lista de asistencias {@link Attendance}
     */
    List<Attendance> listAll();
    /**
     * Obtiene la asistencias paginadas por id
     * @param userId id del usuario
     * @return la asistencia {@link Attendance}
     */
    Page<Attendance> attendaceByUserId(Long userId, Pageable pageable);
}
