package com.escihu.apiescihuvirtual.service.Licenciatura;

import com.escihu.apiescihuvirtual.Dto.Licenciatura.LicenciaturaDtoRequest;
import com.escihu.apiescihuvirtual.persistence.Entity.Licenciatura.Licenciatura;

import java.util.List;

public interface LicenciaturaService {

    /**
     * Lista de todas las licenciaturas.
     * @return Lista de licenciaturas {@link Licenciatura}
     */
    List<Licenciatura> listAll();

    /**
     * Crea una nueva licenciatura.
     *
     * @param licenciaturaDtoRequest objeto con los datos de la licenciatura {@link LicenciaturaDtoRequest}
     * @return la licenciatura creada {@link Licenciatura}
     */
    Licenciatura create(LicenciaturaDtoRequest licenciaturaDtoRequest);
    /**
     * Actualiza una licenciatura.
     *
     * @param licenciaturaId el id de la licenciatura a ser actualizada
     * @param licenciaturaDtoRequest objeto con los nuevos datos de la licenciatura {@link LicenciaturaDtoRequest}
     * @return la licenciatura actualizada {@link Licenciatura}
     */
    Licenciatura update(Long licenciaturaId, LicenciaturaDtoRequest licenciaturaDtoRequest);
    /**
     * Obtiene una licenciatura por su id.
     *
     * @param licenciaturaId el id de la licenciatura
     * @return la licenciatura {@link Licenciatura}
     */
    Licenciatura getById(Long licenciaturaId);
    /**
     * Elimina una licenciatura.
     *
     * @param licenciaturaId el id de la licenciatura a eliminar
     */
    void delete(Long licenciaturaId);
    /**
     * Verifica si una licenciatura existe por su id.
     *
     * @param id el id de la licenciatura a verificar
     * @return true si existe, false si no
     */
    boolean existLicenciatura(Long id);
}
