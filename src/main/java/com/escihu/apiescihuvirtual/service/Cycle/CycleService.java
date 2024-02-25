package com.escihu.apiescihuvirtual.service.Cycle;

import com.escihu.apiescihuvirtual.persistence.Entity.Cycle.Cycle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CycleService {

    public void addCycle(Cycle cycle);

    public Page<Cycle> getAllCycles(Pageable page);

    public void updateCycle(Cycle cycle);

    public Cycle getcycleById(long cycleId);

    public List<Cycle> getActiveCycles();
}
