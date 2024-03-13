package com.escihu.apiescihuvirtual.persistence.Entity.Licenciatura;

import jakarta.persistence.*;
import lombok.Builder;

@Builder
@Entity
@Table(name = "licenciatura")
public class Licenciatura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private short code;

    public Licenciatura(Long id, String name, short code) {
        this.id = id;
        this.name = name;
        this.code = code;
    }

    public Licenciatura() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
