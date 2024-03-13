package com.escihu.apiescihuvirtual.Controller;

import com.escihu.apiescihuvirtual.Dto.LoginRequestDTO;
import com.escihu.apiescihuvirtual.Dto.LoginResponse;
import com.escihu.apiescihuvirtual.Dto.RegistrationDTO;
import com.escihu.apiescihuvirtual.exceptions.UsernameNotFoundException;
import com.escihu.apiescihuvirtual.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    // TODO: Exception en caso del correo sea el mismo
    public ResponseEntity<String> registerUser(@Valid @RequestBody RegistrationDTO registrationDTO) {
        try {
            authenticationService.registerUser(registrationDTO.username(), registrationDTO.email(), registrationDTO.password());
            return ResponseEntity.ok("User registered successfully");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        try {
            System.out.println(loginRequestDTO.username());
            System.out.println(loginRequestDTO.password());
            LoginResponse user = authenticationService.loginUser(loginRequestDTO.username(), loginRequestDTO.password());
            return ResponseEntity.ok(user);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
