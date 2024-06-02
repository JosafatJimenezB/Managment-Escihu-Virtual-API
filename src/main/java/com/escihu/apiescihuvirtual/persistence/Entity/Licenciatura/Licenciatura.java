package com.escihu.apiescihuvirtual.persistence.Entity.Licenciatura;

import com.escihu.apiescihuvirtual.persistence.Entity.Student.Student;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import lombok.Builder;

import java.util.HashSet;
import java.util.Set;

/**
 * La clase Licenciatura representa la entidad Licenciatura de la base de datos.
 * Está mapeada a la tabla "licenciatura" en la base de datos.
 */
@Builder
@Entity
@Table(name = "licenciatura")
public class Licenciatura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    //TODO: agregar validadiones
    // Ejemplo code: 01
    private short code;

    @OneToMany(mappedBy = "licenciatura", cascade = CascadeType.PERSIST)
    @JsonBackReference
    @JsonIgnore
    private Set<Student> students = new HashSet<>();

    public Licenciatura(Long id, String name, short code, Set<Student> students) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.students = students;
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

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
        for (Student student : students) {
            student.setLicenciatura(this);
        }
    }
}
