package com.escihu.apiescihuvirtual.persistence.Entity.Teacher;

import com.escihu.apiescihuvirtual.persistence.Entity.Address.Address;
import com.escihu.apiescihuvirtual.persistence.Entity.Enums.EstadoCivilEnum;
import com.escihu.apiescihuvirtual.persistence.Entity.Enums.SexoEnum;
import com.escihu.apiescihuvirtual.persistence.Entity.Enums.StatusTeacherEnum;
import com.escihu.apiescihuvirtual.persistence.Entity.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.util.Date;
/**
 * La clase Teacher representa la entidad Docente de la base de datos.
 * Está mapeada a la tabla "teachers" en la base de datos.
 */
@Builder
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

    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

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
    // TODO: java: @Builder will ignore the initializing expression entirely. If you want the initializing expression to serve as default,
    //  add @Builder.Default. If it is not supposed to be settable during building, make the field final.
    //  posible bug
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

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "direccion_id", referencedColumnName = "id")
    private Address direccion;

    public Teacher(Long id, String nombre, String apellidoPaterno, String apellidoMaterno, User user, String RFC, String CURP, String cedulaProfesional, StatusTeacherEnum statusDocente, String gradoEstudios, String areaConocimientos, Date fechaNacimiento, String nacionalidad, Date fechaBaja, SexoEnum sexo, EstadoCivilEnum estadoCivil, String tipoSangre, String correoPersonal, String correoEscolar, Address direccion) {
        this.id = id;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.user = user;
        this.RFC = RFC;
        this.CURP = CURP;
        this.cedulaProfesional = cedulaProfesional;
        this.statusDocente = statusDocente;
        this.gradoEstudios = gradoEstudios;
        this.areaConocimientos = areaConocimientos;
        this.fechaNacimiento = fechaNacimiento;
        this.nacionalidad = nacionalidad;
        this.fechaBaja = fechaBaja;
        this.sexo = sexo;
        this.estadoCivil = estadoCivil;
        this.tipoSangre = tipoSangre;
        this.correoPersonal = correoPersonal;
        this.correoEscolar = correoEscolar;
        this.direccion = direccion;
    }

    public Teacher() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getRFC() {
        return RFC;
    }

    public void setRFC(String RFC) {
        this.RFC = RFC;
    }

    public String getCURP() {
        return CURP;
    }

    public void setCURP(String CURP) {
        this.CURP = CURP;
    }

    public String getCedulaProfesional() {
        return cedulaProfesional;
    }

    public void setCedulaProfesional(String cedulaProfesional) {
        this.cedulaProfesional = cedulaProfesional;
    }

    public StatusTeacherEnum getStatusDocente() {
        return statusDocente;
    }

    public void setStatusDocente(StatusTeacherEnum statusDocente) {
        this.statusDocente = statusDocente;
    }

    public String getGradoEstudios() {
        return gradoEstudios;
    }

    public void setGradoEstudios(String gradoEstudios) {
        this.gradoEstudios = gradoEstudios;
    }

    public String getAreaConocimientos() {
        return areaConocimientos;
    }

    public void setAreaConocimientos(String areaConocimientos) {
        this.areaConocimientos = areaConocimientos;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public Date getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(Date fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    public SexoEnum getSexo() {
        return sexo;
    }

    public void setSexo(SexoEnum sexo) {
        this.sexo = sexo;
    }

    public EstadoCivilEnum getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(EstadoCivilEnum estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getTipoSangre() {
        return tipoSangre;
    }

    public void setTipoSangre(String tipoSangre) {
        this.tipoSangre = tipoSangre;
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
