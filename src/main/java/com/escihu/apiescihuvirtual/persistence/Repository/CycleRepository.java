package com.escihu.apiescihuvirtual.persistence.Repository;

import com.escihu.apiescihuvirtual.persistence.Entity.Cycle.Cycle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CycleRepository extends JpaRepository<Cycle, Long> {
    public List<Cycle> findByClosedAtIsNull();
}
