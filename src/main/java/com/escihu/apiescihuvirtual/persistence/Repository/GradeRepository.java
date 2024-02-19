package com.escihu.apiescihuvirtual.persistence.Repository;

import com.escihu.apiescihuvirtual.persistence.Entity.Grade.Grade;
import com.escihu.apiescihuvirtual.persistence.Entity.Subject.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {

    public List<Grade> findBySubject(Subject subject);
}
