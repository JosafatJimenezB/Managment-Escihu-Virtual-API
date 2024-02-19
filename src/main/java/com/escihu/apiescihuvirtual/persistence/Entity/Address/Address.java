package com.escihu.apiescihuvirtual.persistence.Entity.Address;

import jakarta.persistence.*;

@Entity
@Table(name =  "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String direccion;
    @Column(name = "numero_exterior")
    private int numeroExterior;
    @Column(name = "numero_interior")
    private int numeroInterior;
    private String colonia;
    private String cp;
    private String localidad;
    private String municipio;
    private String estado;

    public Address(Long id, String direccion, int numeroExterior, int numeroInterior, String colonia, String cp, String localidad, String municipio, String estado) {
        this.id = id;
        this.direccion = direccion;
        this.numeroExterior = numeroExterior;
        this.numeroInterior = numeroInterior;
        this.colonia = colonia;
        this.cp = cp;
        this.localidad = localidad;
        this.municipio = municipio;
        this.estado = estado;
    }

    public Address(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getNumeroExterior() {
        return numeroExterior;
    }

    public void setNumeroExterior(int numeroExterior) {
        this.numeroExterior = numeroExterior;
    }

    public int getNumeroInterior() {
        return numeroInterior;
    }

    public void setNumeroInterior(int numeroInterior) {
        this.numeroInterior = numeroInterior;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
