package com.escihu.apiescihuvirtual.persistence.Entity.Student;

import com.escihu.apiescihuvirtual.persistence.Entity.Address.Address;
import com.escihu.apiescihuvirtual.persistence.Entity.Course.Course;
import com.escihu.apiescihuvirtual.persistence.Entity.Enums.EstadoCivilEnum;
import com.escihu.apiescihuvirtual.persistence.Entity.Enums.SexoEnum;
import com.escihu.apiescihuvirtual.persistence.Entity.Enums.StatusStudent;
import com.escihu.apiescihuvirtual.persistence.Entity.Licenciatura.Licenciatura;
import com.escihu.apiescihuvirtual.persistence.Entity.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.util.List;
/**
 * La clase Student representa la entidad Estudiante de la base de datos.
 * Está mapeada a la tabla "students" en la base de datos.
 */
@Builder
@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 7)
    @Column(name = "matricula", length = 7)
    private String matricula;

    @Enumerated(EnumType.STRING)
    private StatusStudent statusAlumno;

    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    private String nombre;
    @Column(name = "apellido_paterno")
    private String apellidoPaterno;
    @Column(name = "apellido_materno")
    private String apellidoMaterno;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "licenciatura_id")
    @JsonManagedReference
    private Licenciatura licenciatura;

    @Column(unique = true)
    private String curp;

    private String nacionalidad;

    @Enumerated(EnumType.STRING)
    private SexoEnum sexo;

    @Column(name = "tipo_sangre")
    private String tipoSangre;

    @Column(name = "estado_civil")
    private EstadoCivilEnum estadoCivil;

    private String telefono;

    private String celular;

    @Column(name = "ingreso_mensual")
    private String ingresoMensual;

    @Column(name = "institucion_procedencia")
    private String institucionProcedencia;

    @Column(name = "institucion_procedencia_estado")
    private String institucionProcedenciaEstado;

    @Column(name = "institucion_procedencia_municipio")
    private String institucionProcedenciaMunicipio;

    @Column(name = "correo_personal", unique = true)
    private String correoPersonal;

    @Column(name = "correo_escolar", unique = true)
    private String correoEscolar;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "direccion_id")
    private Address direccion;

    @Column(name = "nss")
    private String nss;

    @JsonIgnore
    @ManyToMany(mappedBy = "students", fetch = FetchType.LAZY)
    private List<Course> courses;


    public Student(Long id, String matricula, StatusStudent statusAlumno, User user, String nombre, String apellidoPaterno, String apellidoMaterno, Licenciatura licenciatura, String curp, String nacionalidad, SexoEnum sexo, String tipoSangre, EstadoCivilEnum estadoCivil, String telefono, String celular, String ingresoMensual, String institucionProcedencia, String institucionProcedenciaEstado, String institucionProcedenciaMunicipio, String correoPersonal, String correoEscolar, Address direccion, String nss, List<Course> courses) {
        this.id = id;
        this.matricula = matricula;
        this.statusAlumno = statusAlumno;
        this.user = user;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.licenciatura = licenciatura;
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
        this.nss = nss;
        this.courses = courses;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getNss() {
        return nss;
    }

    public void setNss(String nss) {
        this.nss = nss;
    }

    public Student() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
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

    public SexoEnum getSexo() {
        return sexo;
    }

    public void setSexo(SexoEnum sexo) {
        this.sexo = sexo;
    }

    public String getTipoSangre() {
        return tipoSangre;
    }

    public void setTipoSangre(String tipoSangre) {
        this.tipoSangre = tipoSangre;
    }

    public EstadoCivilEnum getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(EstadoCivilEnum estadoCivil) {
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

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}
