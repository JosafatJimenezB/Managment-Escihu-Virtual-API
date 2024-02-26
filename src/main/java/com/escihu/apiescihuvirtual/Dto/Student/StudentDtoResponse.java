package com.escihu.apiescihuvirtual.Dto.Student;

import com.escihu.apiescihuvirtual.persistence.Entity.Address.Address;
import com.escihu.apiescihuvirtual.persistence.Entity.Enums.StatusStudent;
import lombok.Builder;

@Builder
public class StudentDtoResponse {

    private Long id;
    private StatusStudent statusAlumno;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String nombreCarrera;

    public StudentDtoResponse(Long id, StatusStudent statusAlumno, String nombre, String apellidoPaterno, String apellidoMaterno, String nombreCarrera) {
        this.id = id;
        this.statusAlumno = statusAlumno;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.nombreCarrera = nombreCarrera;
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

    public String getNombreCarrera() {
        return nombreCarrera;
    }

    public void setNombreCarrera(String nombreCarrera) {
        this.nombreCarrera = nombreCarrera;
    }
}
