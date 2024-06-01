package com.escihu.apiescihuvirtual.service.Licenciatura;

import com.escihu.apiescihuvirtual.Dto.Licenciatura.LicenciaturaDtoRequest;
import com.escihu.apiescihuvirtual.persistence.Entity.Licenciatura.Licenciatura;
import com.escihu.apiescihuvirtual.persistence.Repository.LicenciaturaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LicenciaturaServiceImpl implements LicenciaturaService {

    private final LicenciaturaRepository licenciaturaRepository;

    public LicenciaturaServiceImpl(LicenciaturaRepository licenciaturaRepository) {
        this.licenciaturaRepository = licenciaturaRepository;
    }

    @Override
    public List<Licenciatura> listAll() {
        return licenciaturaRepository.findAll();
    }

    @Override
    public Licenciatura create(LicenciaturaDtoRequest licenciaturaDtoRequest) {
        Licenciatura licenciatura = new Licenciatura();

        licenciatura.setName(licenciaturaDtoRequest.getName());
        licenciatura.setCode(licenciaturaDtoRequest.getCode());

        return licenciaturaRepository.save(licenciatura);
    }

    @Override
    public Licenciatura update(Long licenciaturaId, LicenciaturaDtoRequest licenciaturaDtoRequest) {
        Optional<Licenciatura> licenciaturaExist = licenciaturaRepository.findById(licenciaturaId);

        if (!licenciaturaExist.isPresent()) {
            return null;
        }

        Licenciatura licenciatura = licenciaturaExist.get();

        licenciatura.setName(licenciaturaDtoRequest.getName());
        licenciatura.setCode(licenciaturaDtoRequest.getCode());

        return licenciaturaRepository.save(licenciatura);
    }

    @Override
    public Licenciatura getById(Long licenciaturaId) {
        return licenciaturaRepository.findById(licenciaturaId).orElse(null);
    }

    @Override
    public void delete(Long licenciaturaId) {
        licenciaturaRepository.deleteById(licenciaturaId);
    }

    @Override
    public boolean existLicenciatura(Long id) {
        return !licenciaturaRepository.existsById(id);
    }
}
