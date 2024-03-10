package com.escihu.apiescihuvirtual.service.Cycle;

import com.escihu.apiescihuvirtual.Dto.Cycle.CycleDtoRequest;
import com.escihu.apiescihuvirtual.persistence.Entity.Cycle.Cycle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CycleService {

    public Cycle addCycle(CycleDtoRequest cycle);

    public Page<Cycle> getAllCycles(Pageable page);

    public Cycle updateCycle(CycleDtoRequest cycleDtoRequest, Long id);

    public Cycle getcycleById(long cycleId);

    public List<Cycle> getActiveCycles();

    public boolean exists(Long id);
}
