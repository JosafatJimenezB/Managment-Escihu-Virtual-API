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
import com.escihu.apiescihuvirtual.utils.UserUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TeacherServiceImpl implements TeacherService{

    private final TeacherRepository teacherRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public TeacherServiceImpl(TeacherRepository teacherRepository, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.teacherRepository = teacherRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public Teacher createTeacher(TeacherDtoRequest teacherDtoRequest) {
        String username = teacherDtoRequest.getNombre().toLowerCase() + teacherDtoRequest.getApellidoPaterno().toLowerCase();
        String email = UserUtils.generateEmail(teacherDtoRequest.getNombre(), teacherDtoRequest.getApellidoPaterno(), userRepository);

        Role TeacherRole = roleRepository.findByAuthority("TEACHER")
                .orElseThrow(() -> new RuntimeException("Teacher Role not found"));

        User user = new User(
                username,
                email,
                passwordEncoder.encode(UserUtils.generateRandomPassword()),
                Set.of(TeacherRole)
        );

        userRepository.save(user);

        Teacher teacher = Teacher.builder()
                .nombre(teacherDtoRequest.getNombre())
                .apellidoPaterno(teacherDtoRequest.getApellidoPaterno())
                .apellidoMaterno(teacherDtoRequest.getApellidoMaterno())
                .CURP(teacherDtoRequest.getCURP())
                .cedulaProfesional(teacherDtoRequest.getCedulaProfesional())
                .RFC(teacherDtoRequest.getRFC())
                .areaConocimientos(teacherDtoRequest.getAreaConocimientos())
                .correoPersonal(teacherDtoRequest.getCorreoPersonal())
                .fechaNacimiento(teacherDtoRequest.getFechaNacimiento())
                .estadoCivil(teacherDtoRequest.getEstadoCivil())
                .gradoEstudios(teacherDtoRequest.getGradoEstudios())
                .sexo(teacherDtoRequest.getSexo())
                .correoEscolar(email)
                .user(user)
                .fechaBaja(null)
                .tipoSangre(teacherDtoRequest.getTipoSangre())
                .nacionalidad(teacherDtoRequest.getNacionalidad())
                .direccion(teacherDtoRequest.getDireccion())
                .statusDocente(teacherDtoRequest.getStatusDocente())
                .build();

        return teacherRepository.save(teacher);
    }

    @Override
    public Teacher updateTeacher(Long id, TeacherDtoRequest teacherDtoRequest) {
        Optional<Teacher> teacherExists = teacherRepository.findById(id);

        if(!teacherExists.isPresent()){
            return null;
        }

        Teacher teacher = teacherExists.get();

        teacher = Teacher.builder()
                .nombre(teacherDtoRequest.getNombre())
                .apellidoPaterno(teacherDtoRequest.getApellidoPaterno())
                .apellidoMaterno(teacherDtoRequest.getApellidoMaterno())
                .CURP(teacherDtoRequest.getCURP())
                .cedulaProfesional(teacherDtoRequest.getCedulaProfesional())
                .RFC(teacherDtoRequest.getRFC())
                .areaConocimientos(teacherDtoRequest.getAreaConocimientos())
                .correoPersonal(teacherDtoRequest.getCorreoPersonal())
                .fechaNacimiento(teacherDtoRequest.getFechaNacimiento())
                .estadoCivil(teacherDtoRequest.getEstadoCivil())
                .gradoEstudios(teacherDtoRequest.getGradoEstudios())
                .sexo(teacherDtoRequest.getSexo())
                .correoEscolar(teacherDtoRequest.getCorreoEscolar())
                .fechaBaja(teacherDtoRequest.getFechaBaja())
                .tipoSangre(teacherDtoRequest.getTipoSangre())
                .nacionalidad(teacherDtoRequest.getNacionalidad())
                .direccion(teacherDtoRequest.getDireccion())
                .statusDocente(teacherDtoRequest.getStatusDocente())
                .build();

        return teacherRepository.save(teacher);
    }

    @Override
    public Page<Teacher> getAllTeachers(Pageable pageable) {

        return teacherRepository.findAll(pageable);
    }

    @Override
    public PaginatedTeacherDtoResponse listTeachersPaginated(Pageable pageable) {
        Page<Teacher> teachersPage = teacherRepository.findAll(pageable);

        List<TeacherDtoResponse> teachersDto = teachersPage.getContent().stream()
                .map(teacher -> new TeacherDtoResponse(
                        teacher.getId(),
                        teacher.getNombre(),
                        teacher.getApellidoPaterno(),
                        teacher.getApellidoMaterno(),
                        teacher.getAreaConocimientos())
                )
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
                .map(teacher -> new TeacherDtoResponse(
                        teacher.getId(),
                        teacher.getNombre(),
                        teacher.getApellidoPaterno(),
                        teacher.getApellidoMaterno(),
                        teacher.getAreaConocimientos())
                )
                .collect(Collectors.toList());

        return new PageImpl<>(teachersDto, pageable, teachersPage.getTotalElements());
    }

    @Override
    public List<TeacherDtoResponse> listTeachers() {

        List<Teacher> teachers = teacherRepository.findAll();

        return teachers.stream()
                .map(teacher -> new TeacherDtoResponse(
                        teacher.getId(),
                        teacher.getNombre(),
                        teacher.getApellidoPaterno(),
                        teacher.getApellidoMaterno(),
                        teacher.getAreaConocimientos()
                )).collect(Collectors.toList());
    }

    @Override
    public Teacher getTeacherById(Long id) {
        return teacherRepository.findById(id).orElse(null);
    }

    @Override
    public boolean exists(Long id) {
        return teacherRepository.existsById(id);
    }
}
