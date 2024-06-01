package com.escihu.apiescihuvirtual.service.Cycle;

import com.escihu.apiescihuvirtual.Dto.Cycle.CycleDtoRequest;
import com.escihu.apiescihuvirtual.persistence.Entity.Cycle.Cycle;
import com.escihu.apiescihuvirtual.persistence.Repository.CycleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CycleServiceImpl implements CycleService {

    private final CycleRepository cycleRepository;

    public CycleServiceImpl(CycleRepository cycleRepository) {
        this.cycleRepository = cycleRepository;
    }

    @Override
    public Cycle addCycle(CycleDtoRequest cycleDtoRequest) {

        Cycle cycle = Cycle.builder()
                .name(cycleDtoRequest.getName())
                .build();

        return cycleRepository.save(cycle);
    }

    @Override
    public Page<Cycle> getAllCycles(Pageable page) {
        return cycleRepository.findAll(page);
    }

    @Override
    public Cycle updateCycle(CycleDtoRequest cycleDtoRequest, Long cycleId) {
        Optional<Cycle> cycleExist = cycleRepository.findById(cycleId);

        if (!cycleExist.isPresent()) {
            return null;
        }

        Cycle cycle = cycleExist.get();
        cycle.setName(cycleDtoRequest.getName());

        return cycleRepository.save(cycle);
    }

    @Override
    public Cycle getcycleById(long cycleId) {
        return cycleRepository.findById(cycleId).orElse(null);
    }

    @Override
    public List<Cycle> getActiveCycles() {
        return cycleRepository.findByClosedAtIsNull();
    }

    @Override
    public boolean existsCycle(Long cycleId) {
        return cycleRepository.existsById(cycleId);
    }
}
