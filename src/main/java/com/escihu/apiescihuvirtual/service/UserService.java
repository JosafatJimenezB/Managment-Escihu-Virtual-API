package com.escihu.apiescihuvirtual.service;


import com.escihu.apiescihuvirtual.Dto.Users.ChangePasswordRequest;
import com.escihu.apiescihuvirtual.Dto.Users.UserDtoResponse;
import com.escihu.apiescihuvirtual.persistence.Entity.Role;
import com.escihu.apiescihuvirtual.persistence.Entity.Student.Student;
import com.escihu.apiescihuvirtual.persistence.Entity.Teacher.Teacher;
import com.escihu.apiescihuvirtual.persistence.Entity.User;
import com.escihu.apiescihuvirtual.persistence.Repository.StudentRepository;
import com.escihu.apiescihuvirtual.persistence.Repository.TeacherRepository;
import com.escihu.apiescihuvirtual.persistence.Repository.UserRepository;
import jakarta.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * UserService is a service class that handles operations related to users.
 * It implements the UserDetailsService interface from Spring Security.
 * It provides methods for loading a user by username, finding a user by username, and saving a user.
 * It uses the UserRepository to perform these operations.
 *
 */
@Service
public class UserService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;


    /**
     * Constructs a new UserService with the specified UserRepository.
     *
     * @param userRepository the UserRepository to be used by the UserService
     */
    public UserService(UserRepository userRepository, StudentRepository studentRepository, TeacherRepository teacherRepository, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    /**
     * Loads a user by the specified username.
     * If the user does not exist, a UsernameNotFoundException is thrown.
     *
     * @param username the username of the user to load
     * @return a UserDetails object containing the user's details
     * @throws UsernameNotFoundException if the user does not exist
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("in the user details service");

        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public Page<UserDtoResponse> listUsersPaginated(Pageable pageable) {

        Page<User> usersPage = userRepository.findAll(pageable);



        List<UserDtoResponse> usersDto = usersPage.getContent().stream()
                .map(user -> {
                    Student studentDto = null;
                    Teacher teacherDto = null;

                    if (user.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("STUDENT"))) {
                    Student student = studentRepository.findByUserUserId(user.getUserId());
                    if(student != null) {
                        studentDto = Student.builder()
                                .id(student.getId())
                                .nombre(student.getNombre())
                                .apellidoPaterno(student.getApellidoPaterno())
                                .apellidoMaterno(student.getApellidoMaterno())
                                .matricula(student.getMatricula())
                                .licenciatura(student.getLicenciatura())
                                .courses(student.getCourses())
                                .nacionalidad(student.getNacionalidad())
                                .sexo(student.getSexo())
                                .estadoCivil(student.getEstadoCivil())
                                .build();
                        }
                    }
                    if (user.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("TEACHER"))) {
                        Teacher teacher = teacherRepository.findByUserUserId(user.getUserId());
                        if(teacher != null) {
                            teacherDto = Teacher.builder()
                                    .id(teacher.getId())
                                    .nombre(teacher.getNombre())
                                    .apellidoPaterno(teacher.getApellidoPaterno())
                                    .apellidoMaterno(teacher.getApellidoMaterno())
                                    .RFC(teacher.getRFC())
                                    .CURP(teacher.getCURP())
                                    .cedulaProfesional(teacher.getCedulaProfesional())
                                    .statusDocente(teacher.getStatusDocente())
                                    .gradoEstudios(teacher.getGradoEstudios())
                                    .areaConocimientos(teacher.getAreaConocimientos())
                                    .fechaNacimiento(teacher.getFechaNacimiento())
                                    .nacionalidad(teacher.getNacionalidad())
                                    .fechaBaja(teacher.getFechaBaja())
                                    .sexo(teacher.getSexo())
                                    .estadoCivil(teacher.getEstadoCivil())
                                    .tipoSangre(teacher.getTipoSangre())
                                    .correoPersonal(teacher.getCorreoPersonal())
                                    .correoEscolar(teacher.getCorreoEscolar())
                                    .direccion(teacher.getDireccion())
                                    .build();
                        }
                        }

                        Set<Role> roles = user.getAuthorities().stream()
                                .map(grantedAuthority -> {
                                    Role role = new Role();
                                    role.setAuthority(grantedAuthority.getAuthority());
                                    return role;
                                })
                                .collect(Collectors.toSet());

                        return UserDtoResponse.builder()
                                .id(user.getUserId())
                                .username(user.getUsername())
                                .email(user.getEmail())
                                .student(studentDto)
                                .teacher(teacherDto)
                                .role(roles)
                                .build();
                })
                .collect(Collectors.toList());


        return new PageImpl<>(usersDto, pageable, usersPage.getTotalElements());

    }

    /**
     * Finds a user by the specified username.
     *
     * @param username the username of the user to find
     * @return an Optional containing the user if found, or an empty Optional if not found
     */
    public Optional<User> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Saves a user with the specified details and role.
     * The user's role is added to a set of roles, which is then assigned to the user.
     * The user is then saved in the UserRepository.
     *
     * @param user the user to save
     * @param role the role to assign to the user
     */
    public void saveUser(User user, Role role) {
        Set<Role> roles = new HashSet<>();
        Student student = user.getStudent();
        Teacher teacher = user.getTeacher();

        roles.add(role);
        logger.info(String.format("Saving user with username %s and de role %s", user.getUsername(), role.getAuthority()));
        userRepository.save(new User(user.getUserId(), user.getUsername(), user.getEmail(), user.getPassword(), student,teacher, roles));
    }

    /**
     * Changes the role of a user with the specified username.
     *
     * @param request {@link ChangePasswordRequest } the data transfer
     * @param connectedUser the user that is connected
     */
    public void changePassword(ChangePasswordRequest request, Principal connectedUser) {
        String username = connectedUser.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        System.out.println("username: " + username);
        System.out.println("current password: " + request.getCurrentPassword());
        System.out.println("new password: " + request.getNewPassword());
        System.out.println("confirm password: " + request.getConfirmPassword());
        if(!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())){
            throw new IllegalStateException("Wrong password");
        }

        if(!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new IllegalStateException("Password are not the same");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        userRepository.save(user);
    }


    /**
     * Sends an email to the user to change the password.
     *
     * @param email a string with the user email
     */
    public void forgotPassword(String email) {
        userRepository.findByEmail(email).orElseThrow(
                () -> new IllegalStateException("Email not found" + email)

        );
        try {
            emailService.sendForgotPasswordEmail(email);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Changes the password of the user.
     *
     * @param email a string with the user email
     * @param password a string the new password
     */
    public void setPassword(String email, String password) {
User user = userRepository.findByEmail(email).orElseThrow(
                () -> new IllegalStateException("Email not found" + email)
        );

        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);

    }
}
