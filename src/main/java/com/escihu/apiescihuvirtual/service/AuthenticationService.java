package com.escihu.apiescihuvirtual.service;

import com.escihu.apiescihuvirtual.Dto.LoginResponse;
import com.escihu.apiescihuvirtual.exceptions.EmailAlreadyTakenException;
import com.escihu.apiescihuvirtual.exceptions.UsernameAlreadyTakenException;
import com.escihu.apiescihuvirtual.persistence.Entity.Role;
import com.escihu.apiescihuvirtual.persistence.Entity.User;
import com.escihu.apiescihuvirtual.persistence.Repository.RoleRepository;
import com.escihu.apiescihuvirtual.persistence.Repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * Servicio para autenticar y registrar usuarios
 */
@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationService(UserRepository userRepository, AuthenticationManager authenticationManager, TokenService tokenService, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Registra un usuario en la base de datos
     * @param username un string con el nombre de usuario
     * @param email un string con el correo del usuario
     * @param password un string con la contraseña del usuario
     */
    public void registerUser(String username, String email, String password) {

        if (isUsersExists(username)) {
            throw new UsernameAlreadyTakenException("Username already taken");
        }

        if (isEmailExists(email)) {
            throw new EmailAlreadyTakenException("Email already taken");
        }

        // Investigar si hay una mejor forma de hacer esto

        if (userRepository.count() == 0) {
            Role adminRole = roleRepository.findByAuthority("ADMIN")
                    .orElseThrow(() -> new RuntimeException("Role not found"));
            Set<Role> authorities = new HashSet<>();

            authorities.add(adminRole);
            String encodedPassword = passwordEncoder.encode(password);

            userRepository.save(new User(username, email, encodedPassword, authorities));
            return;
        }

        Role userRole = roleRepository.findByAuthority("STUDENT")
                .orElseThrow(() -> new RuntimeException("Student not found"));
        Set<Role> authorities = new HashSet<>();
        authorities.add(userRole);
        String encodedPassword = passwordEncoder.encode(password);
        User user = new User(username, email, encodedPassword, authorities);
        userRepository.save(user);

    }

    /**
     * Autentica un usuario en el sistema
     * @param username un string con el nombre de usuario
     * @param password un string con la contraseña del usuario
     * @return un objeto {@link LoginResponse} con la información del usuario
     */
    public LoginResponse loginUser(String username, String password) {

        if (!isUsersExists(username)) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));
        String token = tokenService.generateJwt(auth);
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        Role role = (Role) user.getAuthorities().stream().findFirst().orElseThrow(() -> new RuntimeException("User has no role"));

        return new LoginResponse(user.getUserId(), username, token, user.getStudent(), user.getTeacher(), role);
    }

    /**
     * Verifica si un usuario ya existe en la base de datos
     * @param username un string con el nombre de usuario
     * @return un true si el usuario ya existe, false en caso contrario
     */
    private boolean isUsersExists(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    /**
     * Verifica si un correo ya existe en la base de datos
     * @param email un string con el correo del usuario
     * @return un true si el correo ya existe, false en caso contrario
     */
    private boolean isEmailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

}


