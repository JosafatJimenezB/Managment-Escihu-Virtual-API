package com.escihu.apiescihuvirtual.service.GradeDetails;

import com.escihu.apiescihuvirtual.persistence.Entity.Grade.GradeDetail;

public interface GradeDetailsService {

    public GradeDetail getScoreById(long id);

    public GradeDetail updateScore(GradeDetail scoreForm);

    public boolean existById(Long id);
}
