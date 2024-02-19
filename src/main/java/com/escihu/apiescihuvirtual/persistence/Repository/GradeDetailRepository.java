package com.escihu.apiescihuvirtual.persistence.Repository;

import com.escihu.apiescihuvirtual.persistence.Entity.Grade.Grade;
import com.escihu.apiescihuvirtual.persistence.Entity.Grade.GradeDetail;
import com.escihu.apiescihuvirtual.persistence.Entity.Student.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GradeDetailRepository extends JpaRepository<GradeDetail, Long> {
    public Optional<GradeDetail> findByGradeAndStudent(Grade grade, Student student);
}
