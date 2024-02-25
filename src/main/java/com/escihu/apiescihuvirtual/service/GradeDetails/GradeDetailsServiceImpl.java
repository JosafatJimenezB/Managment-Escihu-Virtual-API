package com.escihu.apiescihuvirtual.service.GradeDetails;

import com.escihu.apiescihuvirtual.persistence.Entity.Grade.GradeDetail;
import com.escihu.apiescihuvirtual.persistence.Repository.GradeDetailRepository;
import org.springframework.stereotype.Service;

@Service
public class GradeDetailsServiceImpl implements GradeDetailsService{

    private final GradeDetailRepository gradeDetailRepository;

    public GradeDetailsServiceImpl(GradeDetailRepository gradeDetailRepository) {
        this.gradeDetailRepository = gradeDetailRepository;
    }

    @Override
    public GradeDetail getScoreById(long id) {
        return gradeDetailRepository.getOne(id);
    }

    @Override
    public GradeDetail updateScore(GradeDetail scoreForm) {
        GradeDetail score = gradeDetailRepository.getOne(scoreForm.getId());
        score.setScore(scoreForm.getScore());

        gradeDetailRepository.save(score);

        return score;
    }

}
