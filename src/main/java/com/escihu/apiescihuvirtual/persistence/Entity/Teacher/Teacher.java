package com.escihu.apiescihuvirtual.persistence.Entity.Teacher;

import com.escihu.apiescihuvirtual.persistence.Entity.Address.Address;
import com.escihu.apiescihuvirtual.persistence.Entity.Enums.EstadoCivilEnum;
import com.escihu.apiescihuvirtual.persistence.Entity.Enums.SexoEnum;
import com.escihu.apiescihuvirtual.persistence.Entity.Enums.StatusTeacherEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.util.Date;

@Entity
@Table(name = "teachers")
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @JoinColumn(name = "apellido_paterno")
    private String apellidoPaterno;

    @JoinColumn(name = "apellido_materno")
    private String apellidoMaterno;

    @Column(unique = true)
    private String RFC;

    @Size(max = 18)
    @Column(unique = true)
    private String CURP;

    @Column(name = "cedula_profesional")
    private String cedulaProfesional;

    @Enumerated(EnumType.STRING)
    private StatusTeacherEnum statusDocente;

    @Column(name = "grado_estudios")
    private String gradoEstudios;

    @Column(name = "area_conocimientos")
    private String areaConocimientos;

    @Column(name = "fecha_nacimiento")
    private Date fechaNacimiento;

    private String nacionalidad;

    @Column(name = "fecha_baja")
    private Date fechaBaja = null;

    @Enumerated(EnumType.STRING)
    private SexoEnum sexo;

    @Enumerated(EnumType.STRING)
    private EstadoCivilEnum estadoCivil;

    @Column(name = "tipo_sangre")
    private String tipoSangre;

    @Column(name = "correo_personal")
    private String correoPersonal;

    @Column(name = "correo_escolar")
    private String correoEscolar;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "direccion_id")
    private Address direccion;
}
