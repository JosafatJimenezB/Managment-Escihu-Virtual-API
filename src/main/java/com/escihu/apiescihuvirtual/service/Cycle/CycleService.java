package com.escihu.apiescihuvirtual.service.Cycle;

import com.escihu.apiescihuvirtual.Dto.Cycle.CycleDtoRequest;
import com.escihu.apiescihuvirtual.persistence.Entity.Cycle.Cycle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CycleService {
    /**
     * Crea un nuevo ciclo
     * @param cycle objeto con los datos del ciclo
     * @return el ciclo creado {@link Cycle}
     */
    Cycle addCycle(CycleDtoRequest cycle);

    /**
     * Obtiene todos los ciclos
     * @param page objeto con los datos de paginación
     * @return lista de ciclos {@link Cycle}
     */
    Page<Cycle> getAllCycles(Pageable page);
    /**
     * Actualiza un ciclo
     * @param cycleDtoRequest objeto con los datos del ciclo actualizados
     * @param cycleId id del ciclo
     * @return el ciclo actualizado {@link Cycle}
     */
    Cycle updateCycle(CycleDtoRequest cycleDtoRequest, Long cycleId);
    /**
     * Obtiene un ciclo por id
     * @param cycleId id del ciclo
     * @return el ciclo {@link Cycle}
     */
    Cycle getcycleById(long cycleId);
    /**
     * Obtiene los ciclos activos
     * @return lista de ciclos activos {@link Cycle}
     */
    List<Cycle> getActiveCycles();
    /**
     * Verifica si existe un ciclo
     * @param cycleId id del ciclo
     * @return true si existe, false si no
     */
    boolean existsCycle(Long cycleId);
}
