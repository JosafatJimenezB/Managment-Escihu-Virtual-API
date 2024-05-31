package com.escihu.apiescihuvirtual.service.Teacher;

import com.escihu.apiescihuvirtual.Dto.Teacher.PaginatedTeacherDtoResponse;
import com.escihu.apiescihuvirtual.Dto.Teacher.TeacherDtoRequest;
import com.escihu.apiescihuvirtual.Dto.Teacher.TeacherDtoResponse;
import com.escihu.apiescihuvirtual.persistence.Entity.Role;
import com.escihu.apiescihuvirtual.persistence.Entity.Teacher.Teacher;
import com.escihu.apiescihuvirtual.persistence.Entity.User;
import com.escihu.apiescihuvirtual.persistence.Repository.RoleRepository;
import com.escihu.apiescihuvirtual.persistence.Repository.TeacherRepository;
import com.escihu.apiescihuvirtual.persistence.Repository.UserRepository;
import com.escihu.apiescihuvirtual.service.EmailService;
import com.escihu.apiescihuvirtual.utils.UserUtils;
import jakarta.mail.MessagingException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public TeacherServiceImpl(TeacherRepository teacherRepository, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.teacherRepository = teacherRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }


    @Override
    public Teacher createTeacher(TeacherDtoRequest teacherDtoRequest) {
        String username = teacherDtoRequest.getNombre().toLowerCase()
                + teacherDtoRequest.getApellidoPaterno().toLowerCase();

        String email = UserUtils.generateEmail(
                teacherDtoRequest.getNombre(),
                teacherDtoRequest.getApellidoPaterno(),
                userRepository);

        String password = UserUtils.generateRandomPassword();

        Role TeacherRole = roleRepository.findByAuthority("TEACHER")
                .orElseThrow(() -> new RuntimeException("Teacher Role not found"));

        User user = new User(
                username,
                email,
                passwordEncoder.encode(password),
                Set.of(TeacherRole)
        );

        userRepository.save(user);

        Teacher teacher = mapToTeacher(teacherDtoRequest, user, email);

        try {
            emailService.sendUserCredencials(teacher.getCorreoPersonal(), username, password);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email", e);

        }

        return teacherRepository.save(teacher);
    }

    @Override
    public Teacher updateTeacher(Long id, TeacherDtoRequest teacherDtoRequest) {
        return teacherRepository.findById(id)
                .map( existingTeacher -> {
                    Teacher updatedTeacher = updateTeacherData(existingTeacher, teacherDtoRequest);
                    return teacherRepository.save(updatedTeacher);
                }).orElse(null);

    }

    @Override
    public Page<Teacher> getAllTeachers(Pageable pageable) {

        return teacherRepository.findAll(pageable);
    }

    @Override
    public PaginatedTeacherDtoResponse listTeachersPaginated(Pageable pageable) {
        Page<Teacher> teachersPage = teacherRepository.findAll(pageable);

        List<TeacherDtoResponse> teachersDto = teachersPage.getContent().stream()
                .map(this::mapToTeacherDtoResponse)
                .collect(Collectors.toList());

        return new PaginatedTeacherDtoResponse(
                teachersDto,
                teachersPage.getNumber(),
                teachersPage.getTotalPages(),
                teachersPage.getSize(),
                teachersPage.getNumberOfElements()
        );
    }

    @Override
    public Page<TeacherDtoResponse> teachersClassicPagination(Pageable pageable) {
        Page<Teacher> teachersPage = teacherRepository.findAll(pageable);

        List<TeacherDtoResponse> teachersDto = teachersPage.getContent().stream()
                .map(this::mapToTeacherDtoResponse)
                .collect(Collectors.toList());

        return new PageImpl<>(teachersDto, pageable, teachersPage.getTotalElements());
    }

    @Override
    public List<TeacherDtoResponse> listTeachers() {

        List<Teacher> teachers = teacherRepository.findAll();

        return teachers.stream()
                .map(this::mapToTeacherDtoResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Teacher getTeacherById(Long id) {
        return teacherRepository.findById(id).orElse(null);
    }

    @Override
    public boolean exists(Long id) {
        return teacherRepository.existsById(id);
    }

    private TeacherDtoResponse mapToTeacherDtoResponse(Teacher teacher) {
        return new TeacherDtoResponse(
            teacher.getId(),
            teacher.getNombre(),
            teacher.getApellidoPaterno(),
            teacher.getApellidoMaterno(),
            teacher.getAreaConocimientos()
        );
    }

    private Teacher updateTeacherData(Teacher existingTeacher, TeacherDtoRequest teacherDtoRequest) {
        existingTeacher.setNombre(teacherDtoRequest.getNombre());
        existingTeacher.setApellidoPaterno(teacherDtoRequest.getApellidoPaterno());
        existingTeacher.setApellidoMaterno(teacherDtoRequest.getApellidoMaterno());
        existingTeacher.setCURP(teacherDtoRequest.getCURP());
        existingTeacher.setCedulaProfesional(teacherDtoRequest.getCedulaProfesional());
        existingTeacher.setRFC(teacherDtoRequest.getRFC());
        existingTeacher.setAreaConocimientos(teacherDtoRequest.getAreaConocimientos());
        existingTeacher.setCorreoPersonal(teacherDtoRequest.getCorreoPersonal());
        existingTeacher.setFechaNacimiento(teacherDtoRequest.getFechaNacimiento());
        existingTeacher.setEstadoCivil(teacherDtoRequest.getEstadoCivil());
        existingTeacher.setGradoEstudios(teacherDtoRequest.getGradoEstudios());
        existingTeacher.setSexo(teacherDtoRequest.getSexo());
        existingTeacher.setCorreoEscolar(teacherDtoRequest.getCorreoEscolar());
        existingTeacher.setFechaBaja(teacherDtoRequest.getFechaBaja());
        existingTeacher.setTipoSangre(teacherDtoRequest.getTipoSangre());
        existingTeacher.setNacionalidad(teacherDtoRequest.getNacionalidad());
        existingTeacher.setDireccion(teacherDtoRequest.getDireccion());
        existingTeacher.setStatusDocente(teacherDtoRequest.getStatusDocente());
        return existingTeacher;
    }

    private Teacher mapToTeacher(TeacherDtoRequest request, User user, String email) {
        return Teacher.builder()
                .nombre(request.getNombre())
                .apellidoPaterno(request.getApellidoPaterno())
                .apellidoMaterno(request.getApellidoMaterno())
                .CURP(request.getCURP())
                .cedulaProfesional(request.getCedulaProfesional())
                .RFC(request.getRFC())
                .areaConocimientos(request.getAreaConocimientos())
                .correoPersonal(request.getCorreoPersonal())
                .fechaNacimiento(request.getFechaNacimiento())
                .estadoCivil(request.getEstadoCivil())
                .gradoEstudios(request.getGradoEstudios())
                .sexo(request.getSexo())
                .correoEscolar(email)
                .user(user)
                .fechaBaja(null)
                .tipoSangre(request.getTipoSangre())
                .nacionalidad(request.getNacionalidad())
                .direccion(request.getDireccion())
                .statusDocente(request.getStatusDocente())
                .build();

    }
}
