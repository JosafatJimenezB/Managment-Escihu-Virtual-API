package com.escihu.apiescihuvirtual.service.GradeDetails;

import com.escihu.apiescihuvirtual.persistence.Entity.Grade.GradeDetail;
import com.escihu.apiescihuvirtual.persistence.Repository.GradeDetailRepository;
import org.springframework.stereotype.Service;

@Service
public class GradeDetailsServiceImpl implements GradeDetailsService {

    private final GradeDetailRepository gradeDetailRepository;

    public GradeDetailsServiceImpl(GradeDetailRepository gradeDetailRepository) {
        this.gradeDetailRepository = gradeDetailRepository;
    }

    @Override
    public GradeDetail getScoreById(long scoreId) {
        return gradeDetailRepository.findById(scoreId).orElse(null);
    }

    @Override
    public GradeDetail updateScore(GradeDetail scoreForm) {
        GradeDetail score = gradeDetailRepository.findById(scoreForm.getId()).orElse(null);
        score.setScore(scoreForm.getScore());

        gradeDetailRepository.save(score);

        return score;
    }

    @Override
    public boolean existScore(Long scoreId) {
        return !gradeDetailRepository.existsById(scoreId);
    }

}
