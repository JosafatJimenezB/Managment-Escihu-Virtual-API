package com.escihu.apiescihuvirtual.service.Licenciatura;

import com.escihu.apiescihuvirtual.Dto.Licenciatura.LicenciaturaDtoRequest;
import com.escihu.apiescihuvirtual.persistence.Entity.Licenciatura.Licenciatura;
import org.springframework.stereotype.Service;

import java.util.List;

public interface LicenciaturaService {

    public List<Licenciatura> listAll();

    public Licenciatura create(LicenciaturaDtoRequest licenciaturaDtoRequest);

    public Licenciatura update(Long id, LicenciaturaDtoRequest licenciaturaDtoRequest);

    public Licenciatura getById(Long id);

    public void delete(Long id);

    public boolean existById(Long id);
}
