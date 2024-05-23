package com.escihu.apiescihuvirtual.service.Student;

import com.escihu.apiescihuvirtual.Dto.Student.*;
import com.escihu.apiescihuvirtual.persistence.Entity.Enums.StatusStudent;
import com.escihu.apiescihuvirtual.persistence.Entity.Licenciatura.Licenciatura;
import com.escihu.apiescihuvirtual.persistence.Entity.Role;
import com.escihu.apiescihuvirtual.persistence.Entity.Student.Student;
import com.escihu.apiescihuvirtual.persistence.Entity.User;
import com.escihu.apiescihuvirtual.persistence.Repository.LicenciaturaRepository;
import com.escihu.apiescihuvirtual.persistence.Repository.RoleRepository;
import com.escihu.apiescihuvirtual.persistence.Repository.StudentRepository;
import com.escihu.apiescihuvirtual.persistence.Repository.UserRepository;
import com.escihu.apiescihuvirtual.utils.UserUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService{

    private final StudentRepository studentRepository;

    private final LicenciaturaRepository licenciaturaRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;


    public StudentServiceImpl(StudentRepository studentRepository, LicenciaturaRepository licenciaturaRepository, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.studentRepository = studentRepository;
        this.licenciaturaRepository = licenciaturaRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public Student createStudent(StudentDtoRequest studentDtoRequest) {

        String username = studentDtoRequest.getNombre().toLowerCase() + "." + studentDtoRequest.getApellidoPaterno().toLowerCase();
        //TODO: Investigar como evitar psar el repositorio
        String email = UserUtils.generateEmail(studentDtoRequest.getNombre(), studentDtoRequest.getApellidoPaterno(), userRepository);
        String password = UserUtils.generateRandomPassword();
        Role studentRole = roleRepository.findByAuthority("STUDENT")
                .orElseThrow(() -> new RuntimeException("Student Role not found"));
        // Se crea el usuario
        User user = new User(
                username,
                email,
                passwordEncoder.encode(generateRandomPassword()),
                Set.of(studentRole)
        );
        // Se guarda en la base de datos
        userRepository.save(user);

        // Se crea el estudiante con el usuario generado
        Student student = Student.builder()
                .nombre(studentDtoRequest.getNombre())
                .apellidoPaterno(studentDtoRequest.getApellidoPaterno())
                .apellidoMaterno(studentDtoRequest.getApellidoMaterno())
                .celular(studentDtoRequest.getCelular())
                .curp(studentDtoRequest.getCurp())
                .correoEscolar(email)
                .user(user)
                .estadoCivil(studentDtoRequest.getEstadoCivil())
                .sexo(studentDtoRequest.getSexo())
                .correoPersonal(studentDtoRequest.getCorreoPersonal())
                .nacionalidad(studentDtoRequest.getNacionalidad())
                .ingresoMensual(studentDtoRequest.getIngresoMensual())
                .direccion(studentDtoRequest.getDireccion())
                .matricula(generarMatricula(studentDtoRequest.getLicenciatura().getCode()))
                .tipoSangre(studentDtoRequest.getTipoSangre())
                .statusAlumno(StatusStudent.PROCESO_INSCRIPCION)
                .institucionProcedenciaEstado(studentDtoRequest.getInstitucionProcedenciaEstado())
                .telefono(studentDtoRequest.getTelefono())
                .institucionProcedencia(studentDtoRequest.getInstitucionProcedencia())
                .institucionProcedenciaMunicipio(studentDtoRequest.getInstitucionProcedenciaMunicipio())
                .build();

        Optional<Licenciatura> licenciatura = licenciaturaRepository.findById(studentDtoRequest.getLicenciatura().getId());

        if(!licenciatura.isPresent()){
            student.setLicenciatura(null);
        }

        licenciatura.ifPresent(student::setLicenciatura);


        return studentRepository.save(student);
    }

    @Override
    public Student updateStudent(Long id, StudentUpdateDtoRequest studentDtoRequest) {
        Optional<Student> studentExists = studentRepository.findById(id);

        if(!studentExists.isPresent()){
            return null;
        }

        Student student = studentExists.get();

        student = Student.builder()
                .nombre(studentDtoRequest.getNombre())
                .apellidoPaterno(studentDtoRequest.getApellidoPaterno())
                .apellidoMaterno(studentDtoRequest.getApellidoMaterno())
                .celular(studentDtoRequest.getCelular())
                .curp(studentDtoRequest.getCurp())
                .correoEscolar(null)
                .estadoCivil(studentDtoRequest.getEstadoCivil())
                .sexo(studentDtoRequest.getSexo())
                .correoPersonal(studentDtoRequest.getCorreoPersonal())
                .nacionalidad(studentDtoRequest.getNacionalidad())
                .ingresoMensual(studentDtoRequest.getIngresoMensual())
                .direccion(studentDtoRequest.getDireccion())
                .tipoSangre(studentDtoRequest.getTipoSangre())
                .statusAlumno(studentDtoRequest.getStatusAlumno())
                .institucionProcedenciaEstado(studentDtoRequest.getInstitucionProcedenciaEstado())
                .telefono(studentDtoRequest.getTelefono())
                .institucionProcedencia(studentDtoRequest.getInstitucionProcedencia())
                .institucionProcedenciaMunicipio(studentDtoRequest.getInstitucionProcedenciaMunicipio())
                .build();

        Optional<Licenciatura> licenciatura = licenciaturaRepository.findById(studentDtoRequest.getLicenciatura().getId());

        if(!licenciatura.isPresent()){
            student.setLicenciatura(null);
        }

        licenciatura.ifPresent(student::setLicenciatura);

        return studentRepository.save(student);
    }

    @Override
    public PaginatedStudentDtoResponse listStudentsPaginated(Pageable pageable) {
        Page<Student> studentsPage = studentRepository.findAll(pageable);

        List<StudentDtoResponse> studentsDto = studentsPage.getContent().stream()
                .map(student -> new StudentDtoResponse(
                        student.getId(),
                        student.getStatusAlumno(),
                        student.getNombre(),
                        student.getApellidoPaterno(),
                        student.getApellidoMaterno(),
                        student.getLicenciatura())
                ).collect(Collectors.toList());

        return new PaginatedStudentDtoResponse(
                studentsDto,
                studentsPage.getNumber(),
                studentsPage.getTotalPages(),
                studentsPage.getSize()
        );
    }

    @Override
    public List<StudentDtoResponse> listStudents() {

        List<Student> students = studentRepository.findAll();

        return students.stream()
                .map(student -> new StudentDtoResponse(
                        student.getId(),
                        student.getStatusAlumno(),
                        student.getNombre(),
                        student.getApellidoPaterno(),
                        student.getApellidoMaterno(),
                        student.getLicenciatura())
                ).collect(Collectors.toList());
    }

    @Override
    public Student getStudentById(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    @Override
    public boolean exists(Long id) {
        return studentRepository.existsById(id);
    }

    public static String generarMatricula(int licCode) {
        LocalDateTime now = LocalDateTime.now();
        int year = now.getYear() % 100; // Obtiene los dos últimos dígitos del año

        Random random = new Random();
        int randomNum = random.nextInt(1000); // Genera un número aleatorio de 3 dígitos


        return String.format("%02d%02d%03d", year, licCode, randomNum);
    }

}
