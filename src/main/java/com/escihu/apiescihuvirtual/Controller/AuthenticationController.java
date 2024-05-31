package com.escihu.apiescihuvirtual.Controller;

import com.escihu.apiescihuvirtual.Dto.LoginRequestDTO;
import com.escihu.apiescihuvirtual.Dto.LoginResponse;
import com.escihu.apiescihuvirtual.Dto.RegistrationDTO;
import com.escihu.apiescihuvirtual.exceptions.UsernameNotFoundException;
import com.escihu.apiescihuvirtual.service.AuthenticationService;
import com.escihu.apiescihuvirtual.service.TokenService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "*")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final TokenService tokenService;
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);


    public AuthenticationController(AuthenticationService authenticationService, TokenService tokenService) {
        this.authenticationService = authenticationService;
        this.tokenService = tokenService;
    }

    @PostMapping("/register")
    // TODO: Exception en caso del correo sea el mismo
    public ResponseEntity<String> registerUser(@Valid @RequestBody RegistrationDTO registrationDTO) {
        try {
            logger.info("Intentando registrar usuario: {}", registrationDTO.username());
            authenticationService.registerUser(registrationDTO.username(), registrationDTO.email(), registrationDTO.password());
            logger.info("Usuario registrado: {}", registrationDTO.username());
            return ResponseEntity.ok("User registered successfully");
        } catch (Exception e) {
            logger.error("Error al registrar usuario: {}", registrationDTO.username());
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(HttpServletResponse res, @Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        try {
            logger.info("Intentando loguear usuario: {}", loginRequestDTO.username());
            LoginResponse user = authenticationService.loginUser(loginRequestDTO.username(), loginRequestDTO.password());
            logger.info("Usuario logueado: {}", loginRequestDTO.username());
            return ResponseEntity.ok(user);
        } catch (UsernameNotFoundException e) {
            logger.error("Usuario no encontrado: {}", loginRequestDTO.username());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e) {
            logger.error("Error al loguear usuario: {}", loginRequestDTO.username());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
