package com.escihu.apiescihuvirtual.service.user;


import com.escihu.apiescihuvirtual.Dto.Users.ChangePasswordRequest;
import com.escihu.apiescihuvirtual.Dto.Users.UserDtoResponse;
import com.escihu.apiescihuvirtual.persistence.Entity.Role;
import com.escihu.apiescihuvirtual.persistence.Entity.Student.Student;
import com.escihu.apiescihuvirtual.persistence.Entity.Teacher.Teacher;
import com.escihu.apiescihuvirtual.persistence.Entity.User;
import com.escihu.apiescihuvirtual.persistence.Repository.StudentRepository;
import com.escihu.apiescihuvirtual.persistence.Repository.TeacherRepository;
import com.escihu.apiescihuvirtual.persistence.Repository.UserRepository;
import com.escihu.apiescihuvirtual.service.EmailService;
import jakarta.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
 * Una service class que maneja las operaciones de los usuarios.
 */
@Service
public class UserService {

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
     * Lista los usuarios paginados.
     *
     * @param pageable el objeto pageable
     * @return un objeto Page con los usuarios
     */
    public Page<UserDtoResponse> listUsersPaginated(Pageable pageable) {

        Page<User> usersPage = userRepository.findAll(pageable);

        List<UserDtoResponse> usersDto = usersPage.getContent().stream()
                .map(this::mapToUserDtoResponse)
                .collect(Collectors.toList());


        return new PageImpl<>(usersDto, pageable, usersPage.getTotalElements());

    }

    /**
     * Encuentra un usuario por su username.
     *
     * @param username el username del usuario a buscar
     * @return un objeto Optional con el usuario si existe o un objeto vacio si no existe
     */
    public Optional<User> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Guardar un usuario. El usuario se guarda con el rol especificado.
     * El rol del usuario se agrega a un conjunto de roles, que luego se asigna al usuario.
     * El usuario se guarda en el UserRepository.
     *
     * @param user {@link User} el usuario a guardar
     * @param role {@link Role} el rol del usuario
     */
    public void saveUser(User user, Role role) {
        Set<Role> roles = new HashSet<>();
        Student student = user.getStudent();
        Teacher teacher = user.getTeacher();

        roles.add(role);
        logger.info(String.format("Saving user with username %s and de role %s", user.getUsername(), role.getAuthority()));
        userRepository.save(new User(user.getUserId(), user.getUsername(), user.getEmail(), user.getPassword(), null, student, teacher, roles));
    }

    /**
     * Cambia la contraseña de un usuario del usuario authenticado
     * Changes the role of a user with the specified username.
     *
     * @param request       {@link ChangePasswordRequest } la solicitud de cambio de contraseña
     * @param connectedUser el usuario autenticado
     */
    public void changePassword(ChangePasswordRequest request, Principal connectedUser) {
        String username = connectedUser.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        logger.info(String.format("Changing password for user with username %s", user.getUsername()));
        validatePasswordChange(request, user);

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }


    /**
     * Envia un correo electronico al usuario que olvido su contraseña.
     *
     * @param email el correo electronico del usuario
     */
    public void forgotPassword(String email) {

        if (!emailExists(email)) {
            throw new IllegalStateException("Email not found");
        }

        try {
            emailService.sendForgotPasswordEmail(email);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send forgot password email", e);
        }
    }


    /**
     * Cambia la contraseña de un usuario.
     *
     * @param email    a string with the user email
     * @param password a string the new password
     */
    public void setPassword(String email, String password) {

        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new IllegalStateException("Email not found" + email)
        );

        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);

    }

    /**
     * Mapea un objeto User a un objeto  {@link UserDtoResponse}.
     * @param user {@link User} el usuario a mapear
     * @return regresa un objeto {@link UserDtoResponse}
     */
    private UserDtoResponse mapToUserDtoResponse(User user) {
        Student student = user.getStudent();
        Teacher teacher = user.getTeacher();

        Set<Role> roles = user.getAuthorities().stream()
                .map(role -> new Role(role.getAuthority()))
                .collect(Collectors.toSet());

        return UserDtoResponse.builder()
                .id(user.getUserId())
                .username(user.getUsername())
                .email(user.getEmail())
                .student(student)
                .teacher(teacher)
                .role(roles)
                .build();

    }

    /**
     * Valida el cambio de contraseña comprabando si las contraseñas
     * son iguales y si la contraseña actual es correcta.
     * @param request {@link ChangePasswordRequest} la solicitud de cambio de contraseña
     * @param user {@link User} el usuario que solicita el cambio de contraseña
     */
    private void validatePasswordChange(ChangePasswordRequest request, User user) {
        logger.info(String.format("Validating password change for user with username %s", user.getUsername()));
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            logger.error(String.format("Wrong password for user with username %s", user.getUsername()));
            throw new IllegalStateException("Wrong password");
        }

        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            logger.error(String.format("Passwords are not the same for user with username %s", user.getUsername()));
            throw new IllegalStateException("Password are not the same");
        }
    }

    /**
     * Comprueba si un correo electronico ya existe en la base de datos.
     * @param email un string con el correo electronico
     * @return regresa true si el correo electronico ya existe, de lo contrario regresa false
     */
    private boolean emailExists(String email) {
        logger.info(String.format("Checking if email %s exists", email));
        return studentRepository.findByCorreoPersonal(email) != null || userRepository.findByEmail(email).isPresent();
    }

}
