package com.escihu.apiescihuvirtual.service.Cycle;

import com.escihu.apiescihuvirtual.persistence.Entity.Cycle.Cycle;
import com.escihu.apiescihuvirtual.persistence.Repository.CycleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CycleServiceImpl implements CycleService{

    private final CycleRepository cycleRepository;

    public CycleServiceImpl(CycleRepository cycleRepository) {
        this.cycleRepository = cycleRepository;
    }

    @Override
    public void addCycle(Cycle cycle) {
        cycleRepository.save(cycle);
    }

    @Override
    public Page<Cycle> getAllCycles(Pageable page) {
        return cycleRepository.findAll(page);
    }

    @Override
    public void updateCycle(Cycle cycleForm) {
        Cycle cycle = cycleRepository.findById(cycleForm.getId()).orElse(null);

        cycle.setName(cycleForm.getName());

        cycleRepository.save(cycle);
    }

    @Override
    public Cycle getcycleById(long cycleId) {
        return cycleRepository.findById(cycleId).orElse(null);
    }

    @Override
    public List<Cycle> getActiveCycles() {
        return cycleRepository.findByClosedAtIsNull();
    }
}
