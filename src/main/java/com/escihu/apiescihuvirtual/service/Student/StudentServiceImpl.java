package com.escihu.apiescihuvirtual.service.Student;

import com.escihu.apiescihuvirtual.Dto.Student.StudentDtoRequest;
import com.escihu.apiescihuvirtual.Dto.Student.StudentDtoResponse;
import com.escihu.apiescihuvirtual.Dto.Student.StudentUpdateDtoRequest;
import com.escihu.apiescihuvirtual.persistence.Entity.Enums.StatusStudent;
import com.escihu.apiescihuvirtual.persistence.Entity.Licenciatura.Licenciatura;
import com.escihu.apiescihuvirtual.persistence.Entity.Role;
import com.escihu.apiescihuvirtual.persistence.Entity.Student.Student;
import com.escihu.apiescihuvirtual.persistence.Entity.User;
import com.escihu.apiescihuvirtual.persistence.Repository.LicenciaturaRepository;
import com.escihu.apiescihuvirtual.persistence.Repository.RoleRepository;
import com.escihu.apiescihuvirtual.persistence.Repository.StudentRepository;
import com.escihu.apiescihuvirtual.persistence.Repository.UserRepository;
import com.escihu.apiescihuvirtual.service.EmailService;
import com.escihu.apiescihuvirtual.utils.UserUtils;
import jakarta.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    private final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);
    private final StudentRepository studentRepository;
    private final LicenciaturaRepository licenciaturaRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;


    public StudentServiceImpl(StudentRepository studentRepository, LicenciaturaRepository licenciaturaRepository, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.studentRepository = studentRepository;
        this.licenciaturaRepository = licenciaturaRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }


    @Override
    public Student createStudent(StudentDtoRequest studentDtoRequest) {

        String[] name = studentDtoRequest.getNombre().split(" ");

        String username = name[0].toLowerCase() + "." + studentDtoRequest.getApellidoPaterno().toLowerCase();

        //TODO: Investigar como evitar psar el repositorio
        String email = UserUtils.generateEmail(studentDtoRequest.getNombre(), studentDtoRequest.getApellidoPaterno(), userRepository);
        String password = UserUtils.generateRandomPassword();
        Role studentRole = roleRepository.findByAuthority("STUDENT")
                .orElseThrow(() -> new RuntimeException("Student Role not found"));
        // Se crea el usuario
        User user = new User(
                username,
                email,
                passwordEncoder.encode(password),
                Set.of(studentRole)
        );
        // Se guarda en la base de datos
        userRepository.save(user);


        // Se crea el estudiante con el usuario generado
        Student student = mapToStudent(studentDtoRequest, user, email);

        Optional<Licenciatura> licenciatura = licenciaturaRepository.findById(studentDtoRequest.getLicenciatura().getId());

        if (licenciatura.isEmpty()) {
            logger.error("Licenciatura not found");
            student.setLicenciatura(null);
        }

        licenciatura.ifPresent(student::setLicenciatura);

        try {
            emailService.sendUserCredencials(student.getCorreoPersonal(), username, password);
        } catch (MessagingException e) {
            logger.error("Failed to send email to the student with username {}", username, e);

        }

        return studentRepository.save(student);
    }

    @Override
    public Student updateStudent(Long studentId, StudentUpdateDtoRequest studentDtoRequest) {
        Optional<Student> studentExists = studentRepository.findById(studentId);

        if (!studentExists.isPresent()) {
            return null;
        }

        Student student = studentExists.get();

        updateStudentData(student, studentDtoRequest);

        Optional<Licenciatura> licenciatura = licenciaturaRepository.findById(studentDtoRequest.getLicenciatura().getId());

        if (licenciatura.isEmpty()) {
            logger.error("Licenciatura not found");
            student.setLicenciatura(null);
        }

        licenciatura.ifPresent(student::setLicenciatura);

        return studentRepository.save(student);
    }

    @Override
    public Page<StudentDtoResponse> listStudentsPaginated(Pageable pageable) {
        Page<Student> studentsPage = studentRepository.findAll(pageable);

        List<StudentDtoResponse> studentsDto = studentsPage.getContent().stream()
                .map(this::mapStudentToDto)
                .collect(Collectors.toList());

        return new PageImpl<>(studentsDto, pageable, studentsPage.getTotalElements());
    }

    @Override
    public List<StudentDtoResponse> listStudents() {

        List<Student> students = studentRepository.findAll();

        return students.stream()
                .map(this::mapStudentToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Student getStudentById(Long studentId) {
        return studentRepository.findById(studentId).orElse(null);
    }

    @Override
    public Optional<Student> findById(Long studentId) {
        return studentRepository.findById(studentId);
    }


    @Override
    public boolean existsStudent(Long id) {
        return !studentRepository.existsById(id);
    }

    @Override
    public List<Student> findStudentsByStatusAlumno(StatusStudent alumnoStatus) {
        return studentRepository.findAllByStatusAlumno(alumnoStatus);
    }

    private static String generarMatricula(int licCode) {
        LocalDateTime now = LocalDateTime.now();
        int year = now.getYear() % 100; // Obtiene los dos últimos dígitos del año

        Random random = new Random();
        int randomNum = random.nextInt(1000); // Genera un número aleatorio de 3 dígitos

        return String.format("%02d%02d%03d", year, licCode, randomNum);
    }

    private StudentDtoResponse mapStudentToDto(Student student) {
        return new StudentDtoResponse(
                student.getId(),
                student.getStatusAlumno(),
                student.getNombre(),
                student.getApellidoPaterno(),
                student.getApellidoMaterno(),
                student.getLicenciatura()
        );
    }

    private Student mapToStudent(StudentDtoRequest studentDtoRequest, User user,String email) {
        return Student.builder()
                .matricula(generarMatricula(studentDtoRequest.getLicenciatura().getCode()))
                .statusAlumno(studentDtoRequest.getStatusAlumno())
                .nombre(studentDtoRequest.getNombre())
                .apellidoPaterno(studentDtoRequest.getApellidoPaterno())
                .apellidoMaterno(studentDtoRequest.getApellidoMaterno())
                .celular(studentDtoRequest.getCelular())
                .curp(studentDtoRequest.getCurp())
                .estadoCivil(studentDtoRequest.getEstadoCivil())
                .sexo(studentDtoRequest.getSexo())
                .correoPersonal(studentDtoRequest.getCorreoPersonal())
                .nacionalidad(studentDtoRequest.getNacionalidad())
                .ingresoMensual(studentDtoRequest.getIngresoMensual())
                .direccion(studentDtoRequest.getDireccion())
                .tipoSangre(studentDtoRequest.getTipoSangre())
                .user(user)
                .correoEscolar(email)
                .institucionProcedenciaEstado(studentDtoRequest.getInstitucionProcedenciaEstado())
                .telefono(studentDtoRequest.getTelefono())
                .institucionProcedencia(studentDtoRequest.getInstitucionProcedencia())
                .institucionProcedenciaMunicipio(studentDtoRequest.getInstitucionProcedenciaMunicipio())
                .nss(studentDtoRequest.getNss())
                .build();

    }

    private Student updateStudentData(Student existingStudent, StudentUpdateDtoRequest studentDtoRequest) {
        existingStudent.setNombre(studentDtoRequest.getNombre());
        existingStudent.setApellidoPaterno(studentDtoRequest.getApellidoPaterno());
        existingStudent.setApellidoMaterno(studentDtoRequest.getApellidoMaterno());
        existingStudent.setCurp(studentDtoRequest.getCurp());
        existingStudent.setStatusAlumno(studentDtoRequest.getStatusAlumno());
        existingStudent.setNacionalidad(studentDtoRequest.getNacionalidad());
        existingStudent.setSexo(studentDtoRequest.getSexo());
        existingStudent.setTipoSangre(studentDtoRequest.getTipoSangre());
        existingStudent.setEstadoCivil(studentDtoRequest.getEstadoCivil());
        existingStudent.setTelefono(studentDtoRequest.getTelefono());
        existingStudent.setCelular(studentDtoRequest.getCelular());
        existingStudent.setIngresoMensual(studentDtoRequest.getIngresoMensual());
        existingStudent.setInstitucionProcedencia(studentDtoRequest.getInstitucionProcedencia());
        existingStudent.setInstitucionProcedenciaEstado(studentDtoRequest.getInstitucionProcedenciaEstado());
        existingStudent.setInstitucionProcedenciaMunicipio(studentDtoRequest.getInstitucionProcedenciaMunicipio());
        existingStudent.setCorreoPersonal(studentDtoRequest.getCorreoPersonal());
        existingStudent.setDireccion(studentDtoRequest.getDireccion());
        existingStudent.setNss(studentDtoRequest.getNss());
        return existingStudent;
    }

}
