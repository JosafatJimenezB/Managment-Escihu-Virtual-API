package com.escihu.apiescihuvirtual.Dto.Teacher;

import lombok.Builder;

@Builder
public class TeacherDtoResponse {

    private Long id;

    private String nombre;

    private String apellidoPaterno;

    private String apellidoMaterno;

    private String areaConocimientos;

    public TeacherDtoResponse(Long id, String nombre, String apellidoPaterno, String apellidoMaterno, String areaConocimientos) {
        this.id = id;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.areaConocimientos = areaConocimientos;
    }

    public TeacherDtoResponse(){

    }

    public Long getId() {
        return id;
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

    public String getAreaConocimientos() {
        return areaConocimientos;
    }

    public void setAreaConocimientos(String areaConocimientos) {
        this.areaConocimientos = areaConocimientos;
    }
}
