package com.escihu.apiescihuvirtual.Dto.Student;

import com.escihu.apiescihuvirtual.persistence.Entity.Address.Address;
import com.escihu.apiescihuvirtual.persistence.Entity.Enums.StatusStudent;
import lombok.Builder;

@Builder
public class StudentDtoRequest {
    private StatusStudent statusAlumno;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String nombreCarrera;
    private String curp;
    private String nacionalidad;
    private String sexo;
    private String tipoSangre;
    private String estadoCivil;
    private String telefono;
    private String celular;
    private String ingresoMensual;
    private String institucionProcedencia;
    private String institucionProcedenciaEstado;
    private String institucionProcedenciaMunicipio;
    private String correoPersonal;
    private String correoEscolar;
    private Address direccion;

    public StudentDtoRequest(StatusStudent statusAlumno, String nombre, String apellidoPaterno, String apellidoMaterno, String nombreCarrera, String curp, String nacionalidad, String sexo, String tipoSangre, String estadoCivil, String telefono, String celular, String ingresoMensual, String institucionProcedencia, String institucionProcedenciaEstado, String institucionProcedenciaMunicipio, String correoPersonal, String correoEscolar, Address direccion) {
        this.statusAlumno = statusAlumno;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.nombreCarrera = nombreCarrera;
        this.curp = curp;
        this.nacionalidad = nacionalidad;
        this.sexo = sexo;
        this.tipoSangre = tipoSangre;
        this.estadoCivil = estadoCivil;
        this.telefono = telefono;
        this.celular = celular;
        this.ingresoMensual = ingresoMensual;
        this.institucionProcedencia = institucionProcedencia;
        this.institucionProcedenciaEstado = institucionProcedenciaEstado;
        this.institucionProcedenciaMunicipio = institucionProcedenciaMunicipio;
        this.correoPersonal = correoPersonal;
        this.correoEscolar = correoEscolar;
        this.direccion = direccion;
    }

    public StudentDtoRequest(){

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

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getTipoSangre() {
        return tipoSangre;
    }

    public void setTipoSangre(String tipoSangre) {
        this.tipoSangre = tipoSangre;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getIngresoMensual() {
        return ingresoMensual;
    }

    public void setIngresoMensual(String ingresoMensual) {
        this.ingresoMensual = ingresoMensual;
    }

    public String getInstitucionProcedencia() {
        return institucionProcedencia;
    }

    public void setInstitucionProcedencia(String institucionProcedencia) {
        this.institucionProcedencia = institucionProcedencia;
    }

    public String getInstitucionProcedenciaEstado() {
        return institucionProcedenciaEstado;
    }

    public void setInstitucionProcedenciaEstado(String institucionProcedenciaEstado) {
        this.institucionProcedenciaEstado = institucionProcedenciaEstado;
    }

    public String getInstitucionProcedenciaMunicipio() {
        return institucionProcedenciaMunicipio;
    }

    public void setInstitucionProcedenciaMunicipio(String institucionProcedenciaMunicipio) {
        this.institucionProcedenciaMunicipio = institucionProcedenciaMunicipio;
    }

    public String getCorreoPersonal() {
        return correoPersonal;
    }

    public void setCorreoPersonal(String correoPersonal) {
        this.correoPersonal = correoPersonal;
    }

    public String getCorreoEscolar() {
        return correoEscolar;
    }

    public void setCorreoEscolar(String correoEscolar) {
        this.correoEscolar = correoEscolar;
    }

    public Address getDireccion() {
        return direccion;
    }

    public void setDireccion(Address direccion) {
        this.direccion = direccion;
    }
}
