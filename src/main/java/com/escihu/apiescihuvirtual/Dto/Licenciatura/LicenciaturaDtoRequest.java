package com.escihu.apiescihuvirtual.Dto.Licenciatura;

import jakarta.validation.constraints.Max;
import lombok.Builder;

@Builder
public class LicenciaturaDtoRequest {

    private String name;

    // poner un maximo de 2 digitoss
    @Max(value = 2, message = "The code must be a maximum of 2 digits")
    private short code;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public short getCode() {
        return code;
    }

    public void setCode(short code) {
        this.code = code;
    }
}
