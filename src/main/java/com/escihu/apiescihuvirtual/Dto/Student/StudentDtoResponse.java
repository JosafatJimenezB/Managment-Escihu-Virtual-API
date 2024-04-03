package com.escihu.apiescihuvirtual.Dto.Student;

import com.escihu.apiescihuvirtual.persistence.Entity.Address.Address;
import com.escihu.apiescihuvirtual.persistence.Entity.Enums.StatusStudent;
import com.escihu.apiescihuvirtual.persistence.Entity.Licenciatura.Licenciatura;
import lombok.Builder;

@Builder
public class StudentDtoResponse {

    private Long id;
    private StatusStudent statusAlumno;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private Licenciatura licenciatura;

    public StudentDtoResponse(Long id, StatusStudent statusAlumno, String nombre, String apellidoPaterno, String apellidoMaterno, Licenciatura licenciatura) {
        this.id = id;
        this.statusAlumno = statusAlumno;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.licenciatura = licenciatura;
    }

    public StudentDtoResponse() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StatusStudent getStatusAlumno() {
        return statusAlumno;
    }

    public void setStatusAlumno(StatusStudent statusAlumno) {
        this.statusAlumno = statusAlumno;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public Licenciatura getLicenciatura() {
        return licenciatura;
    }

    public void setLicenciatura(Licenciatura licenciatura) {
        this.licenciatura = licenciatura;
    }
}
