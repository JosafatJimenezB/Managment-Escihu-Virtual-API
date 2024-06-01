package com.escihu.apiescihuvirtual.service.GradeDetails;

import com.escihu.apiescihuvirtual.persistence.Entity.Grade.GradeDetail;

public interface GradeDetailsService {

    /**
     * Busca un score por su id.
     *
     * @param scoreId el score a guardar
     * @return el score guardado {@link GradeDetail}
     */
    GradeDetail getScoreById(long scoreId);
    /**
     * Actualiza un score.
     *
     * @param scoreForm el score a actualizar
     * @return el score actualizado {@link GradeDetail}
     */
    GradeDetail updateScore(GradeDetail scoreForm);
    /**
     * Verifica si un score existe por su id.
     *
     * @param scoreId el id del score a verificar
     * @return true si existe, false si no
     */
    boolean existScore(Long scoreId);
}
